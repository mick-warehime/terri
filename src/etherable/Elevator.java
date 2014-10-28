package etherable;


// BUG TO FIX:: WHEN ELEVATOR HITS SOMETHING IT GETS OUT OF PHASE AND RESETS TO THE WRONG PLACE!!!

import org.newdawn.slick.command.Command;
import org.newdawn.slick.tiled.TiledMap;

import commands.LegalDisplaceCommand;

public class Elevator extends EtherObject {


	private int yPos;
	private int speed = 1;
	private int etherSpeed = 1;
	private int range = 200;
	private int elevation = 0;
	private int etherElevation = 0;
	private boolean isMoving = true;
 

	public Elevator(int i, int j, TiledMap map) {
		super(i, j, map);

		yPos = tileY;
		

	}

	protected void setObjectDimensions(){
		this.h = 2*tileSize;
		this.w = 5*tileSize;

	}

	private boolean cantMove(){

		boolean answer = Math.abs(elevation)>range || elevation>0;

		answer = answer || collisionHandler.isCollided(this);
		return answer;
	}



	public void update(int mouseX, int mouseY){
		super.update(mouseX, mouseY);

		//Check for direction change
		if(isMoving){
			if(cantMove()){speed = -speed;}
			elevation = elevation + speed;

			rect.setY(yPos+elevation);
		}

		// the ether version should always update and never change phase
		etherElevation = etherElevation + etherSpeed;
		if(Math.abs(etherElevation)>range || etherElevation>0){
			etherSpeed = -etherSpeed;
		}
		etherRect.setY(tileY+etherElevation);

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
		isMoving = true;
	}

	public void setObjectToEther(){
		super.setObjectToEther();

		isMoving = false;	
		elevation = 0;
	}


	public void restore() {
		super.restore();

		// setting isMoving true catches the case where it was held but not put
		isMoving = true;

		yPos = tileY;
		elevation = etherElevation;
		speed = etherSpeed;
		rect.setLocation(tileX,yPos+elevation);
	}
	
	public void toggle(){
		isMoving = !isMoving;
	}





}
