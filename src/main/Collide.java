package main;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;


// TODOOOO
//
// getObjectId(x,y) --> id of object or nil got a given coordinate

public class Collide {

	private ArrayList<Rectangle> blocks = new ArrayList<Rectangle>(); 
	private ArrayList<Ether> etherObjects = new ArrayList<Ether>(); 

	// This will keep a list of Tiles that are blocked
	private boolean blocked[][];	
	private int tileSize;
	private Rectangle playerRect;

	public Collide(TiledMap map) throws SlickException {
		// TODO Auto-generated constructor stub		
		tileSize = map.getTileHeight();
		initializeMeta(map);
	}

	public void addPlayerRect(Rectangle playerRect){
		this.playerRect = playerRect;
	}

	private void initializeMeta(TiledMap map){
		// This will create an Array with all the Tiles in your map. When set to true, it means that Tile is blocked.
		blocked = new boolean[map.getWidth()][map.getHeight()];

		// Loop through the Tiles and read their Properties

		// Set here the Layer you want to Read. In your case, it'll be layer 1,
		// since the objects are on the second layer.
		int metaIndex = map.getLayerIndex("meta");
		int etherIndex = map.getLayerIndex("ether");


		for(int i = 0; i < map.getWidth(); i++) {
			for(int j = 0; j < map.getHeight(); j++) {

				// Read a Tile
				int tileID = map.getTileId(i, j, metaIndex);

				// Get the value of the Property named "blocked"
				String blockedValue = map.getTileProperty(tileID, "blocked", "false");
				String etherValue = map.getTileProperty(tileID, "ether", "false");
				String etherType = map.getTileProperty(tileID, "type","none" );
				
				// If the value of the Property is "true"...
				if(blockedValue.equals("true")) {

					// We set that index of the TileMap as blocked
					blocked[i][j] = true;

					// And create the collision Rectangle
					blocks.add(new Rectangle(i * tileSize,j * tileSize, tileSize, tileSize));
				}
				if(etherValue.equals("true")) {					
					etherObjects.add(new EtherObject(i,j,false,etherType,map,etherIndex));					
				}
			}
		}	
	}


	public boolean isCollided(Rectangle anotherRect){	
		//	check if collided with permanent solid blocks	
		for(Rectangle r: blocks ){
			if(anotherRect.intersects(r)){
				return true;
			}	
		}
		// check if collided with solid etherable Objects
		for(Ether eObj: etherObjects){
			if(eObj.isCollided(anotherRect)){
				return true;
			}
		}
		return false;
	}

	public boolean isAtEtherObject(int x, int y){

		for(Ether eObj: etherObjects){
			if(eObj.contains(x,y)){
				return true;
			}
		}

		return false;
	}

	public int getEtherObjectId(int x, int y){

		for(int i = 0; i < etherObjects.size(); i++){
			if(etherObjects.get(i).contains(x,y))
				return i;
		}
		// error message... 
		System.out.println("there are no ether objects... why did you ask for one?");
		return -1;

	}

	public boolean activeEtherObjectExists(){
		for(Ether eObj: etherObjects){
			if(eObj.isActive()){
				return true;
			}
		}

		return false;
	}

	public int getActiveEtherObject(){
		for(int i = 0; i < etherObjects.size(); i++){
			if(etherObjects.get(i).isActive())
				return i;
		}
		// error message... 
		System.out.println("there are no ether objects... why did you ask for one?");
		return -1;

	}

	public ArrayList<Ether> getEtherObjects(){
		return etherObjects;
	}

}
