package gameobjects;


// BUG TO FIX:: WHEN ELEVATOR HITS SOMETHING IT GETS OUT OF PHASE AND RESETS TO THE WRONG PLACE!!!


import graphics.EtherGraphics;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import commands.DisplaceCommand;
import commands.MinimumDisplaceCommand;

public class EtherElevator extends EtherObject implements InteractiveCollideable,Rotateable {



	
	private int speed;
	private int range;
	private int displacement = 0;
	private int etherDisplacement = 0;
	private int etherSpeed = 0;
	private boolean isMoving = true;
	private int initialDirection;
	private char xOrY;
	private char originalXOrY;
	

	public EtherElevator(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map,args);


		System.out.println(args);
		this.range = Integer.parseInt((String)args.get("range"))*tileSize;
		
		if (args.containsKey("initialDirection")){
			this.initialDirection = Integer.parseInt((String)args.get("initialDirection"));
		}else{
			initialDirection = 1;
		}
		
		if (args.containsKey("xOrY")){
			this.xOrY = ((String)args.get("xOrY")).charAt(0);
		}else{
			this.xOrY = 'x';
		}		
		this.originalXOrY = xOrY;
		
		initializeMovement(initialDirection);
		
	}

	private boolean cantMove(){

		boolean answer = Math.abs(displacement)>range || displacement<0;
		
		answer = answer || collisionHandler.isCollided(shape);
		
		return answer;
	}



	public void update(){
		super.update();

		if(isMoving){
			//Check for direction change
			if(cantMove()){
				speed = -speed;
			}
			displacement = displacement + speed;

			if(xOrY == 'x'){
				shape.setX(shape.getX() + speed);//xPos+displacement
			}
			else{
				shape.setY(shape.getY() + speed);
			}
		}

	}

	public void put(){
		super.put();
		
	
		
		initializeMovement(speed); 
		
		isMoving = true;


	}

	public void setObjectToEther(){
		super.setObjectToEther();

		isMoving = false;	

		// the ether variables simply hold the pre-put state to make it easy to restore
		etherDisplacement = displacement;
		etherSpeed = speed;
		if (xOrY == 'x'){
			etherShape.setX(shape.getX());
		}else{
			etherShape.setY(shape.getY());
		}
		// reset the range counter for the put elevator
		displacement = 0;
	}


	public void restore() {
		
		super.restore();

		// setting isMoving true catches the case where it was held but not put
		isMoving = true;

		displacement = etherDisplacement;
		speed = etherSpeed;
		
//
//		shape.setLocation(etherShape.getX(),etherShape.getY());
		restoreToOriginalAngle();
	}

	public void toggle(){
		isMoving = !isMoving;
	}



	
	public ArrayList<Command> onCollisionBroadcast(Class collidingObjectClass, Shape collidingObjectShape) {

		ArrayList<Command> list = new ArrayList<Command>();
		if (xOrY == 'x'){
			list.add(new DisplaceCommand(speed, 'x'));
		}else{
			list.add(new MinimumDisplaceCommand(2*speed, 'y'));
		}

		return list;
	}

	@Override
	public void onCollisionDo(Class collidingObjectClass,Shape collidingObjectShape) {
		}


	private void initializeMovement(int direction){
		if (direction > 0){
			displacement = 0;
			speed = 1;
		}else
		{
			displacement = range;
			speed = -1;
		}
	}

	
	
	
	@Override
	public void rotate(boolean rotateClockwise, int[] mousePos) {
		float rotationAngle;
		if (rotateClockwise){ 		
			rotationAngle = (float) (-0.5*Math.PI);		
		}
		else {
			rotationAngle = (float) (0.5*Math.PI);
		}
		// Do it the simple way, for rects only.
		float width = shape.getWidth();
		float height = shape.getHeight();
		
		
		shape = new Rectangle(
				shape.getCenterX() - height/2, shape.getCenterY()-width/2, height,width);
		
		graphics.setRect(shape);			
		graphics.rotateImages(rotationAngle);
		
		if (xOrY == 'x'){ xOrY = 'y';}
		else {xOrY = 'x';}
		
		
	}
	
	@Override
	public void restoreToOriginalAngle() {
		((EtherGraphics)graphics).restoreOriginalAngle();
		this.xOrY = this.originalXOrY;
		
	}




}
