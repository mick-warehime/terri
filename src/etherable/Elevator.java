package etherable;


// BUG TO FIX:: WHEN ELEVATOR HITS SOMETHING IT GETS OUT OF PHASE AND RESETS TO THE WRONG PLACE!!!

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.tiled.TiledMap;

import commands.LegalDisplaceCommand;

public class Elevator extends EtherObject {


	private int yPos;
	private int speed = 1;
	private int range;
	private int elevation = 0;
	private int etherElevation = 0;
	private int etherSpeed = 0;
	private boolean isMoving = true;


	public Elevator(int x, int y, int w, int h, int range, TiledMap map) throws SlickException {
		super(x, y, w, h, map);
		
		// set y position to initial y position 
		yPos = y*tileSize;

		// 		
		this.range = range*tileSize;
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

		//Check for collision with player and displace player accordingly
		if (collisionHandler.isCollidedWithPlayer(this)){
			if (speed>0){
				collisionHandler.addToCommandStack((Command) new LegalDisplaceCommand("+y",speed));
			}
			if (speed<0){
				collisionHandler.addToCommandStack((Command) new LegalDisplaceCommand("-y",-speed)) ;
			}
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
		etherRect.setY(y+etherElevation);
		
		// reset the range counter for the put elevator
		elevation = 0;
	}


	public void restore() {
		super.restore();

		// setting isMoving true catches the case where it was held but not put
		isMoving = true;

		yPos = y;
		
		elevation = etherElevation;
		speed = etherSpeed;
		rect.setLocation(x,yPos+elevation);
	}

	public void toggle(){
		isMoving = !isMoving;
	}





}
