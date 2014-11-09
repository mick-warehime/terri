package gameobjects;


// BUG TO FIX:: WHEN ELEVATOR HITS SOMETHING IT GETS OUT OF PHASE AND RESETS TO THE WRONG PLACE!!!


import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.tiled.TiledMap;

import commands.DisplaceCommand;
import commands.MinimumDisplaceCommand;

public class XElevator extends EtherObject implements InteractiveCollideable {



	private int xPos;
	private int speed = 1;
	private int range;
	private int displacement = 0;
	private int etherDisplacement = 0;
	private int etherSpeed = 0;
	private boolean isMoving = true;


	public XElevator(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map,args);

		// set y position to initial y position 
		xPos = x*tileSize;

		// 		
		this.range = Integer.parseInt((String)args.get("range"))*tileSize;
		
		
		
	}

	private boolean cantMove(){

		boolean answer = Math.abs(displacement)>range || displacement>0;

		answer = answer || collisionHandler.isCollided(this);
		return answer;
	}



	public void update(int mouseX, int mouseY){
		super.update(mouseX, mouseY);

		if(isMoving){
			//Check for direction change
			if(cantMove()){
				speed = -speed;
			}
			displacement = displacement + speed;

			rect.setX(xPos+displacement);
		}


	}

	public void put(int x, int y){
		super.put(x, y);

		xPos = putX;
		displacement = 0;
		isMoving = true;


	}

	public void setObjectToEther(){
		super.setObjectToEther();

		isMoving = false;	

		// the ether variables simply hold the pre-put state to make it easy to restore
		etherDisplacement = displacement;
		etherSpeed = speed;
		etherRect.setX(pixelX+etherDisplacement);

		// reset the range counter for the put elevator
		displacement = 0;
	}


	public void restore() {
		super.restore();

		// setting isMoving true catches the case where it was held but not put
		isMoving = true;

		xPos = pixelX;

		displacement = etherDisplacement;
		speed = etherSpeed;
		rect.setLocation(xPos+displacement,pixelY);
	}

	public void toggle(){
		isMoving = !isMoving;
	}



	
	public ArrayList<Command> onCollisionBroadcast(String collidingObjectClass) {

		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new DisplaceCommand(speed, 'x'));
		return list;





	}

	@Override
	public void onCollisionDo(String collidingObjectClass) {
		// TODO Auto-generated method stub

	}





}
