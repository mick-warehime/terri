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
	
}
