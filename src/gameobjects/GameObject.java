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
	
	protected int pixelX; // (x,y) top left in pixels
	protected int pixelY; 

	protected int tileX; // (I,J) top left in tiles
	protected int tileY;

	protected int pixelHeight; // (height,width) in pixels 
	protected int pixelWidth;

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

		//x y position, height and width, in pixels
		this.pixelX = x*tileSize;
		this.pixelY = y*tileSize;
		
		
		this.pixelHeight = h*tileSize;
		this.pixelWidth = w*tileSize;			
		this.name = name; 


		getSprites(map);

		// used for collision detection		
		setRect();
		

		this.drawer = new TileGraphics(rect, map, x,y,w,h);

	}
	
	

	protected void setRect(){
		rect = new Rectangle(pixelX,pixelY,pixelWidth,pixelHeight);
	}

	public Rectangle getRect(){
		return rect;
	}

	protected void setObjectDimensions(){
		throw new UnsupportedOperationException(); 
	}

	public void draw(int mapX, int mapY, int mouseX, int mouseY){
		drawer.render((int) rect.getX() ,(int) rect.getY(), mapX, mapY, 1); 
	}

	public boolean canCollide(){
		return true;
	}

	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}

	public void update(int mouseX, int mouseY){
//		throw new UnsupportedOperationException(); 
	}

	public int getTileX(){
		return pixelX;		
	}
	public int getTileY(){
		return pixelY;
	}

	public void toggle() {
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


	protected void getSprites(TiledMap map) throws SlickException{

		int etherIndex = map.getLayerIndex("ether");

		for(int i = tileX; i < (tileX+pixelWidth/tileSize); i++){
			for(int j = tileY; j < (tileY+pixelHeight/tileSize); j++){
				sprites.add(map.getTileImage(i,j,etherIndex));
			}
		}
	}

 

	protected long getTime() {
		return System.currentTimeMillis() ;
	}

	public String getName(){
		return name;
	}


}
