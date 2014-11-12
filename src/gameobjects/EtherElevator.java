package gameobjects;


// BUG TO FIX:: WHEN ELEVATOR HITS SOMETHING IT GETS OUT OF PHASE AND RESETS TO THE WRONG PLACE!!!


import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.tiled.TiledMap;

import commands.DisplaceCommand;
import commands.MinimumDisplaceCommand;

public class EtherElevator extends EtherObject implements InteractiveCollideable {



	private int xPos;
	private int yPos;
	private int speed;
	private int range;
	private int displacement = 0;
	private int etherDisplacement = 0;
	private int etherSpeed = 0;
	private boolean isMoving = true;
	private int initialDirection;
	private char xOrY;
	

	public EtherElevator(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map,args);

		// set y position to initial y position 
		xPos = x*tileSize;
		yPos = y*tileSize;

		// 		
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
		
		initializeMovement(initialDirection);
		
	}

	private boolean cantMove(){

		boolean answer = Math.abs(displacement)>range || displacement<0;
		
		answer = answer || collisionHandler.isCollided(this);
		
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
				rect.setX(rect.getX() + speed);//xPos+displacement
			}
			else{
				rect.setY(rect.getY() + speed);
			}
		}

	}

	public void put(){
		super.put();
		
		
		if(xOrY == 'x'){xPos = putX;}
		else{ yPos = putY;}
		
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
			etherRect.setX(rect.getX());
		}else{
			etherRect.setY(rect.getY());
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
		

		rect.setLocation(etherRect.getX(),etherRect.getY());
	}

	public void toggle(){
		isMoving = !isMoving;
	}



	
	public ArrayList<Command> onCollisionBroadcast(String collidingObjectClass) {

		ArrayList<Command> list = new ArrayList<Command>();
		if (xOrY == 'x'){
			list.add(new DisplaceCommand(speed, 'x'));
		}else{
			list.add(new MinimumDisplaceCommand(2*speed, 'y'));
		}

		return list;
	}

	@Override
	public void onCollisionDo(String collidingObjectClass) {
		// TODO Auto-generated method stub

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



}
