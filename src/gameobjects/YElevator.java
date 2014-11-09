package gameobjects;


// BUG TO FIX:: WHEN ELEVATOR HITS SOMETHING IT GETS OUT OF PHASE AND RESETS TO THE WRONG PLACE!!!


import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.tiled.TiledMap;

import commands.GainEffectCommand;
import commands.MinimumDisplaceCommand;

public class YElevator extends EtherObject implements InteractiveCollideable {

	
	
	private int yPos;
	private int speed = 1;
	private int range;
	private int elevation = 0;
	private int etherElevation = 0;
	private int etherSpeed = 0;
	private boolean isMoving = true;


	public YElevator(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map,args);
		
		// set y position to initial y position 
		yPos = y*tileSize;

		// 		
		this.range = Integer.parseInt((String)args.get("range"))*tileSize;
	}

	private boolean cantMove(){

		boolean answer = Math.abs(elevation)>range || elevation>0;

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
			elevation = elevation + speed;

			rect.setY(yPos+elevation);
		}

	
	}

	public void put(int x, int y){
		super.put(x, y);

		yPos = putY;
		elevation = 0;
		isMoving = true;
		

	}

	public void setObjectToEther(){
		super.setObjectToEther();

		isMoving = false;	
		
		// the ether variables simply hold the pre-put state to make it easy to restore
		etherElevation = elevation;
		etherSpeed = speed;
		etherRect.setY(pixelY+etherElevation);
		
		// reset the range counter for the put elevator
		elevation = 0;
	}


	public void restore() {
		super.restore();

		// setting isMoving true catches the case where it was held but not put
		isMoving = true;

		yPos = pixelY;
		
		elevation = etherElevation;
		speed = etherSpeed;
		rect.setLocation(pixelX,yPos+elevation);
	}

	public void toggle(){
		isMoving = !isMoving;
	}

	

	@Override
	public ArrayList<Command> onCollisionBroadcast(String collidingObjectClass) {
		
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new MinimumDisplaceCommand(2*speed, 'y'));
		return list;
		
		
	
		
		
	}

	@Override
	public void onCollisionDo(String collidingObjectClass) {
		// TODO Auto-generated method stub
		
	}





}
