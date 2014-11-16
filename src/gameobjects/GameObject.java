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
	
	
	protected TileGraphics drawer;
	

	protected int tileX; // (I,J) top left in tiles
	protected int tileY;

	protected int tileSize;
	protected int proximity;

	protected String name;

	protected Rectangle rect;
	protected ArrayList<Image> sprites = new ArrayList<Image>(); 
	protected CollisionHandler collisionHandler;

	public GameObject(int tileX, int tileY, int widthInTiles, int heightInTiles, String name, TiledMap map, Properties args) throws SlickException{

		tileSize = map.getTileHeight();				

		//Position in tile index
		this.tileX = tileX;
		this.tileY = tileY;

		this.name = name; 



		// used for collision detection		
		rect = new Rectangle(tileX*tileSize,tileY*tileSize,widthInTiles*tileSize,heightInTiles*tileSize);
		

		this.drawer = new TileGraphics(rect, map, tileX,tileY,widthInTiles,heightInTiles);
	}
	
	//constructor for objects not constructed from data
	public GameObject(String name) {
		this.name = name; 
		
	}

	//For removal of objects by level
	public boolean isDying(){
		return false;
	}

	public Rectangle getRect(){
		return rect;
	}

	protected void setObjectDimensions(){
		throw new UnsupportedOperationException(); 
	}

	public void render(int mapX, int mapY, int mouseX, int mouseY){
		drawer.render((int) rect.getX() ,(int) rect.getY(), mapX, mapY, 1); 
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
