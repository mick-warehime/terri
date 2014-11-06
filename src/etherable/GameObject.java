package etherable;

import java.util.ArrayList;
import java.util.Properties;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class GameObject {
	
	
	protected int x; // (x,y) top left in pixels
	protected int y; 

	protected int I; // (I,J) top left in tiles
	protected int J;

	protected int h; // (height,width) in pixels 
	protected int w;

	protected int tileSize;
	protected int proximity;

	protected String name;

	protected Rectangle rect;
	protected ArrayList<Image> sprites = new ArrayList<Image>(); 
	protected CollisionHandler collisionHandler;

	public GameObject(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException{

		tileSize = map.getTileHeight();				

		//Position in tile index
		this.I = x;
		this.J = y;

		//x y position, height and width, in pixels
		this.x = x*tileSize;
		this.y = y*tileSize;
		
		
		this.h = h*tileSize;
		this.w = w*tileSize;			
		this.name = name; 


		getSprites(map);

		// used for collision detection		
		setRect();

	}
	
	

	protected void setRect(){
		rect = new Rectangle(x,y,w,h);
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
		return x;		
	}
	public int getTileY(){
		return y;
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

		for(int i = I; i < (I+w/tileSize); i++){
			for(int j = J; j < (J+h/tileSize); j++){
				sprites.add(map.getTileImage(i,j,etherIndex));
			}
		}
	}

	protected void drawTiles(int drawX, int drawY, int mapX, int mapY, float opacity) {
		if(sprites.get(0)==null){
			System.out.println("\n ERROR: NO SPRITES DEFINED. CHECK ETHER LAYER FOR MISSING SPRITES. "
					+" "+ drawX/tileSize+" "+drawY/tileSize+"\n");
		}
		int count = 0;
		for(int xi = drawX; xi < drawX+w; xi += tileSize){
			for(int yi = drawY; yi < drawY+h; yi += tileSize){
				//		
				Image im = sprites.get(count);
				im.setAlpha(opacity);
				im.draw(xi-mapX,yi-mapY);
				count ++;			
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
