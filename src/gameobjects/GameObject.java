package gameobjects;

import graphics.TileGraphics;

import java.util.ArrayList;
import java.util.Properties;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class GameObject {
	
	
	protected TileGraphics graphics;
	

	protected int tileX; // (I,J) top left in tiles
	protected int tileY;

	protected int tileSize;
	protected int proximity;

	protected String name;

	protected Rectangle rect;
	protected ArrayList<Image> sprites = new ArrayList<Image>(); 
	protected CollisionHandler collisionHandler;

	public GameObject(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException{

		tileSize = map.getTileHeight();				

		//Position in tile index
		this.tileX = x;
		this.tileY = y;

		this.name = name; 



		// used for collision detection		
		rect = new Rectangle(x*tileSize,y*tileSize,w*tileSize,h*tileSize);
		
		setGraphics(rect,map,x,y,w,h);
		
	}
	
	protected void setGraphics(Rectangle rect, TiledMap map, int x, int y, int w, int h) throws SlickException{
		graphics = new TileGraphics(rect, map, x,y,w,h);
	}

	public Rectangle getRect(){
		return rect;
	}

	protected void setObjectDimensions(){
		throw new UnsupportedOperationException(); 
	}

	public void render(int mapX, int mapY, int mouseX, int mouseY){
		graphics.render(mapX, mapY); 
		
	}

	public boolean canCollide(){
		return true;
	}

	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}

	public void update(){
//		throw new UnsupportedOperationException(); 
	}


	public void toggle() {
		throw new UnsupportedOperationException(); 
	}
	
	public void toggle(int switchNumber) {
		throw new UnsupportedOperationException(); 
	}

	public void setTarget(ArrayList<GameObject> gameObjects) {
		throw new UnsupportedOperationException(); 
	}


	//Checks if an input rect is near the object's rect
	public boolean isNear(Rectangle rectTest) {

		Rectangle slightlyBiggerRect = new Rectangle(rect.getX()-proximity,rect.getY()-proximity,rect.getWidth()+2*proximity,rect.getHeight()+2*proximity);


		return slightlyBiggerRect.intersects(rectTest);
	}


	protected long getTime() {
		return System.currentTimeMillis() ;
	}

	public String getName(){
		return name;
	}


}
