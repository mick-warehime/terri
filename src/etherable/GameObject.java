package etherable;

import java.util.ArrayList;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class GameObject {
	protected int tileX;
	protected int tileY;

	protected int h; 
	protected int w;
	protected int tileSize;
	protected int proximity = 0;
	
	protected Rectangle rect;
	protected ArrayList<Image> sprites = new ArrayList<Image>(); 
	protected CollisionHandler collisionHandler;

	public GameObject(int i, int j,  TiledMap map){
		
		tileSize = map.getTileHeight();				

		tileX = i*tileSize;
		tileY = j*tileSize;	

		setObjectDimensions();
		
		// used for collision detection		
		setRect();

	}

	protected void setRect(){
		rect = new Rectangle(tileX,tileY,w,h);
	}
	
	public Rectangle getRect(){
		return rect;
	}

	protected void setObjectDimensions(){
		throw new UnsupportedOperationException(); 
	}
	
	public void draw(int i, int j, int mouseX, int mouseY){
		throw new UnsupportedOperationException(); 
	}
	
	public boolean canCollide(){
		return true;
	}
	
	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}
	
	public void update(int mouseX, int mouseY){
		throw new UnsupportedOperationException(); 
	}
	
	public int getTileX(){
		return tileX;		
	}
	public int getTileY(){
		return tileY;
	}

	public void toggle() {
		throw new UnsupportedOperationException(); 
		
	}

	public boolean isNear(int x, int y) {
		Rectangle slightlyBiggerRect = new Rectangle(x-proximity,y-proximity,w+2*proximity,h+2*proximity);
		
		return slightlyBiggerRect.contains(x,y);
	}
	
	
	protected void getSprites(int tileI, int tileJ, TiledMap map){
		
		int etherIndex = map.getLayerIndex("ether");

		for(int i = tileI; i < (tileI+w/tileSize); i++){
			for(int j = tileJ; j < (tileJ+h/tileSize); j++){
				//				System.out.println(i+" "+j+" "+tileI+" "+tileJ+" "+w+" "+h);
				sprites.add(map.getTileImage(i,j,etherIndex));
			}
		}
	}
}
