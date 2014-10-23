package etherable;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EtherObject extends GameObject {



	protected int putX;
	protected int putY;

	protected Rectangle etherRect;
	protected boolean isEther = false;
	protected boolean isPut = false;
	protected boolean isActive = false;



	public EtherObject(int i, int j,  TiledMap map) {
		super(i,j,map);
		
 		getSprites(i,j,map);

		// used for collision detection		
		setEtherRect();

	}

	protected void setEtherRect(){
		etherRect = new Rectangle(tileX,tileY,w,h);
	}





	

	
	

	

	public void setObjectToEther(){
		isEther = true;	
		isActive = true;
		isPut = false;
	}
	public void put(int x, int y){

		if(isActive && !isPut){
			this.isPut = true;
			putX = x-w/2;
			putY = y-h/2;

			rect.setLocation(putX,putY);

		}
	}

	public void restore() {
		// TODO Auto-generated method stub
		isEther = false;
		isPut = false;
		isActive = false;

		rect.setLocation(tileX,tileY);
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

				drawTiles(hoverX,hoverY,mapX,mapY,(float) 0.5);
			}
		}else{
			drawTiles((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) 1);
		}		
	}

	private void drawTiles(int X, int Y, int mapX, int mapY, float opacity) {
		int count = 0;
		for(int x = X; x < X+w; x += tileSize){
			for(int y = Y; y < Y+h; y += tileSize){
				//		

				Image im = sprites.get(count);
				im.setAlpha(opacity);
				im.draw(x-mapX,y-mapY);
				count ++;			
			}
		}
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
