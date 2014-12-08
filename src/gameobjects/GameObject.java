package gameobjects;

import graphics.TileGraphics;

import java.util.ArrayList;
import java.util.Properties;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class GameObject {
	
	
	protected TileGraphics graphics;
	

	protected int tileSize;
	protected int proximity;

	protected String name;

	protected Shape shape;
	protected ArrayList<Image> sprites = new ArrayList<Image>(); 
	protected CollisionHandler collisionHandler;

	public GameObject(int tileX, int tileY, int widthInTiles, int heightInTiles, String name, TiledMap map, Properties args) throws SlickException{

		tileSize = map.getTileHeight();				


		this.name = name; 



		// used for collision detection		
		shape = new Rectangle(tileX*tileSize,tileY*tileSize,widthInTiles*tileSize,heightInTiles*tileSize);
		
		setGraphics(shape,map,tileX,tileY,widthInTiles,heightInTiles);
		
	}
	
	//constructor for objects not constructed from data
	public GameObject(String name) {
		this.name = name; 
		
	}
	protected void setGraphics(Shape rect, TiledMap map, int x, int y, int w, int h) throws SlickException{
		graphics = new TileGraphics(rect, map, x,y,w,h);
	}

	//For removal of objects by level
	public boolean isDying(){
		return false;
	}

	public Shape getShape(){
		return shape;
	}

	protected void setObjectDimensions(){
		throw new UnsupportedOperationException(); 
	}

	public void render(int mapX, int mapY){
		graphics.render(mapX, mapY); 
		
	}

	public boolean canCollide(){
		return true;
	}

	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}

	public void update() throws SlickException{
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


	//Checks if an input shape is near the object's shape
	public boolean isNear(Rectangle rectTest) {

		Rectangle slightlyBiggerRect = new Rectangle(shape.getX()-proximity,shape.getY()-proximity,shape.getWidth()+2*proximity,shape.getHeight()+2*proximity);


		return slightlyBiggerRect.intersects(rectTest);
	}


	protected long getTime() {
		return System.currentTimeMillis() ;
	}

	public String getName(){
		return name;
	}


}
