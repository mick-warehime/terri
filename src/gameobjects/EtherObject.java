package gameobjects;


import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EtherObject extends GameObject {

	
	
	protected int putX;
	protected int putY;

	protected Rectangle etherRect;
	protected boolean isEther = false;
	protected boolean isPut = false;
	protected boolean isActive = false;
	protected boolean isTimed = false;


	public EtherObject(int x, int y, int w, int h, String name, TiledMap map,Properties args) throws SlickException {
		super(x, y, w, h, name, map,args);

		// used for collision detection		
		setEtherRect();

	}

	protected void setEtherRect(){
		etherRect = new Rectangle(pixelX,pixelY,pixelWidth,pixelHeight);
	}


	public void setObjectToEther(){
		isEther = true;	
		isActive = true;
		isPut = false;
	}

	public void put(int mouseX, int mouseY){

		if(isActive && !isPut){
			this.isPut = true;
			putX = mouseX-pixelWidth/2;
			putY = mouseY-pixelHeight/2;

			rect.setLocation(putX,putY);

		}
	}

	public void restore() {
		
		if(canRestore()){
			// TODO Auto-generated method stub
			isEther = false;
			isPut = false;
			isActive = false;
			rect.setLocation(pixelX,pixelY);
		}
	}


	public void update(int mouseX, int mouseY){
		if(isActive && !isPut){
			//		eventually used to update doors/elevators etc;
			int hoverX = (mouseX-pixelWidth/2);
			int hoverY = (mouseY-pixelHeight/2);
			rect.setLocation(hoverX,hoverY);			
		}		
	}

	public void draw(int mapX, int mapY, int mouseX, int mouseY){

		if(isEther){ //If ether
			//Draw ether tile
			drawer.render((int)etherRect.getX(),(int)etherRect.getY(),mapX,mapY,(float) 0.5);
			//If it's placed:
			if(isPut){			
				drawer.render((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) 1);
			}else{ //Otherwise
				int hoverX = (mouseX-pixelWidth/2+mapX);
				int hoverY = (mouseY-pixelHeight/2+mapY);
				if(canPut()){
					drawer.render(hoverX,hoverY,mapX,mapY,(float) 0.5);
				}
			}
		}else{
			drawer.render((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) 1);
		}		
	}


	public boolean canPut(){
		boolean answer = !collisionHandler.lineOfSightCollision(this);
		answer = answer && collisionHandler.canPlaceEtherAt(this);
		return answer;
	}

	// check if anything is at the original position  (ether Rect) before restoring 
	public boolean canRestore(){
		boolean answer = !collisionHandler.isCollidedWithObjects(this);
		answer = answer && !collisionHandler.isCollidedWithPlayer(etherRect);
		answer = answer && !collisionHandler.isCollidedWithEnemy(etherRect);
		return answer;
	}

	public Rectangle getEtherRect(){
		return etherRect;
	}

	public boolean isActive(){
		return isActive;
	}

	public boolean isPut(){
		return isPut;
	}

	public boolean canCollide(){
		return isPut || !isActive;
	}

}
