package etherable;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class Elevator2 extends EtherObject {


	private int yPos;
	private int speed = 1;
	private int etherSpeed = 1;
	private int range = 200;
	private int elevation = 0;
	private int etherElevation = 0;
	private boolean isMoving = true;
	
	
	
	public Elevator2(int i, int j, TiledMap map, int layerId) {
		super(i, j, map, layerId);
		
		
		yPos = tileY;
		
	}

	protected void setObjectDimensions(){
		this.h = 2*tileSize;
		this.w = 5*tileSize;
		
	}
	
	private boolean canMove(){
		
		boolean answer = Math.abs(elevation)>range || elevation>0;
		
		
		
		return answer;
	}
	
	public void update(int mouseX, int mouseY){
		super.update(mouseX, mouseY);
//		if(isActive && !isPut){
//			//		eventually used to update doors/elevators etc;
//			int hoverX = (mouseX-w/2);
//			int hoverY = (mouseY-h/2);
//			rect.setLocation(hoverX,hoverY);			
//		}

		if(isMoving){
			elevation = elevation + speed;

			if(canMove()){
				speed = -speed;
			}
			rect.setY(yPos+elevation);
		}

		// the ether version should always update and never change phase
		etherElevation = etherElevation + etherSpeed;

		if(Math.abs(etherElevation)>range){
			etherSpeed = -etherSpeed;
		}else if (etherElevation>0){
			etherSpeed = -etherSpeed;
		}
		etherRect.setY(tileY+etherElevation);
	}
	
	public void put(int x, int y){

		super.put(x, y);
		yPos = putY;			
		isMoving = true;
//		rect.setLocation(putX,yPos+elevation);
//		if(isActive && !isPut){
//			this.isPut = true;
//			putX = x-w/2;
//			putY = y-h/2;
//
//			yPos = putY;			
//			isMoving = true;
//			rect.setLocation(putX,yPos+elevation);
//		}
	}

	
	

}
