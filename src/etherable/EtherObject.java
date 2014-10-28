package etherable;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EtherObject extends GameObject {



	protected int putX;
	protected int putY;

	protected Rectangle etherRect;
	protected boolean isEther = false;
	protected boolean isPut = false;
	protected boolean isActive = false;



	public EtherObject(int gi, int oi,  TiledMap map) {
		super(gi,oi,map);

		// used for collision detection		
		setEtherRect();

	}

	protected void setEtherRect(){
		etherRect = new Rectangle(x,y,w,h);
	}


	public void setObjectToEther(){
		isEther = true;	
		isActive = true;
		isPut = false;
	}
	
	public void put(int mouseX, int mouseY){

		if(isActive && !isPut){
			this.isPut = true;
			putX = mouseX-w/2;
			putY = mouseY-h/2;

			rect.setLocation(putX,putY);

		}
	}

	public void restore() {
		// TODO Auto-generated method stub
		isEther = false;
		isPut = false;
		isActive = false;
		rect.setLocation(x,y);
	}


	public void update(int mouseX, int mouseY){
		if(isActive && !isPut){
			//		eventually used to update doors/elevators etc;
			int hoverX = (mouseX-w/2);
			int hoverY = (mouseY-h/2);
			rect.setLocation(hoverX,hoverY);			
		}		
	}

	public void draw(int mapX, int mapY, int mouseX, int mouseY){

		if(isEther){ //If ether
			//Draw ether tile
			drawTiles((int)etherRect.getX(),(int)etherRect.getY(),mapX,mapY,(float) 0.5);
			//If it's placed:
			if(isPut){
				drawTiles((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) 1);	
			}else{ //Otherwise
				int hoverX = (mouseX-w/2+mapX);
				int hoverY = (mouseY-h/2+mapY);


				// if the dude cant see the location he cant put it there... dont draw it there
				if(canPut()){
					drawTiles(hoverX,hoverY,mapX,mapY,(float) 0.5);
				}
			}
		}else{
			drawTiles((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) 1);
		}		
	}


	public boolean canPut(){
		boolean answer = !collisionHandler.lineOfSightCollision(this);
		answer = answer && collisionHandler.canPlaceEtherAt(this);
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
