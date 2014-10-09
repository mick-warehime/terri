package etherable;

import java.util.ArrayList;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EtherObject {

	
	private int etherLayerId;	

	protected int tileX;
	protected int tileY;
	protected int putX;
	protected int putY;
	protected int h; 
	protected int w;
	protected int tileSize;
	

	protected Rectangle rect;
	protected Rectangle etherRect;
	private boolean isEther = false;
	private boolean isPut = false;
	private boolean isActive = false;
	private ArrayList<Image> sprites = new ArrayList<Image>(); 
	
	protected CollisionHandler collisionHandler;
	
	
	public EtherObject(int i, int j,  TiledMap map, int layerId) {
		etherLayerId = layerId;

		tileSize = map.getTileHeight();				
		
		tileX = i*tileSize;
		tileY = j*tileSize;	
		
		setObjectDimensions();
		// get height/width and images
		getSprites(i,j,map);
		
		
		

		
		// used for collision detection			
		rect = new Rectangle(tileX,tileY,w,h);
		etherRect = new Rectangle(tileX,tileY,w,h);
		
		
	}
	
	
	protected void setObjectDimensions(){
		throw new UnsupportedOperationException(); 
		
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

	public Rectangle getRect(){
		return rect;
	}

	private void getSprites(int tileI, int tileJ, TiledMap map){
		
		
		for(int i = tileI; i < (tileI+w/tileSize); i++){
			for(int j = tileJ; j < (tileJ+h/tileSize); j++){
//				System.out.println(i+" "+j+" "+tileI+" "+tileJ+" "+w+" "+h);
				sprites.add(map.getTileImage(i,j,etherLayerId));
			}
		}
	}

	public boolean isActive(){
		return isActive;
	}

	

	public boolean isPut(){
		return isPut;
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

	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}
	

}
