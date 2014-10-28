package etherable;

import java.util.ArrayList;

import main.CollisionHandler;

import org.newdawn.slick.Image;
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
	protected int proximity = 0;

	protected Rectangle rect;
	protected ArrayList<Image> sprites = new ArrayList<Image>(); 
	protected CollisionHandler collisionHandler;

	public GameObject(int gi, int oi,  TiledMap map){

		tileSize = map.getTileHeight();				

		parseObject(gi,oi,map);

//		setObjectDimensions();
		
		getSprites(map);
		// used for collision detection		
		setRect();

	}

	private void parseObject(int gi, int oi, TiledMap map){
		//		get position in tiles not pixels
		String strX =  map.getObjectProperty(gi, oi, "x", "0" );
		I = Integer.parseInt(strX);
		x = I*tileSize;

		String strY =  map.getObjectProperty(gi, oi, "y", "0" );
		J = Integer.parseInt(strY);
		y = J*tileSize;

		// default duration is set to h/w = 0/0		
		String strH =  map.getObjectProperty(gi, oi, "h", "0" );
		h = Integer.parseInt(strH)*tileSize;

		String strW =  map.getObjectProperty(gi, oi, "w", "0" );
		w = Integer.parseInt(strW)*tileSize;

		if(x==0 || y == 0 ){
			System.out.println("ERROR NOT ALL DEFAULT OBJECT PROPERTIES SET (X,Y) ");
		}

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

		Rectangle slightlyBiggerRect = new Rectangle(rect.getX()-proximity,rect.getY()-proximity,w+2*proximity,h+2*proximity);

		return slightlyBiggerRect.intersects(rectTest);
	}


	protected void getSprites(TiledMap map){
		
		int etherIndex = map.getLayerIndex("ether");

		for(int i = I; i < (I+w/tileSize); i++){
			for(int j = J; j < (J+h/tileSize); j++){
				//				System.out.println(i+" "+j+" "+tileI+" "+tileJ+" "+w+" "+h);
				sprites.add(map.getTileImage(i,j,etherIndex));
			}
		}
	}

	protected void drawTiles(int drawX, int drawY, int mapX, int mapY, float opacity) {
 		if(sprites.get(0)==null){
			System.out.println("\n ERROR: NO SPRITES DEFINED. CHECK ETHER LAYER FOR MISSING SPRITES. \n");
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
}
