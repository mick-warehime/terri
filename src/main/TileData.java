
package main;
//import etherable.Platform;
//import etherable.Elevator;
import etherable.Door;
import etherable.Elevator;
import etherable.GameObject;
import etherable.Platform;
import etherable.Switch;
import etherable.TimedElevator;
import etherable.TimedPlatform;
import etherable.TimedSwitch;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import actors.Enemy;


public class TileData {

	private ArrayList<Rectangle> blocks = new ArrayList<Rectangle>(); 	
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	// This will keep a list of Tiles that are blocked
	private int tileSize;

	public TileData(TiledMap map) throws SlickException {
		// TODO Auto-generated constructor stub		
		tileSize = map.getTileHeight();

		initializeMeta(map);
		initializeObjects(map);
	}


	private void initializeObjects(TiledMap map) throws NumberFormatException, SlickException{

		int objectGroupCount = map.getObjectGroupCount();
		for( int gi=0; gi < objectGroupCount; gi++ ) // gi = object group index
		{
			int objectCount = map.getObjectCount(gi);
			for( int oi=0; oi < objectCount; oi++ ) // oi = object index
			{
				String objectType =  map.getObjectType(gi,oi);				
				
				// PARSE GAME OBJECT FOR DIMENSION
				int x = map.getObjectX(gi,oi)/tileSize;
				int y = map.getObjectY(gi,oi)/tileSize;
				int h = map.getObjectHeight(gi,oi)/tileSize;
				int w = map.getObjectWidth(gi,oi)/tileSize;
								
				if(objectType.equals("platform")){					
					gameObjects.add(new Platform(x, y, w, h, map));
				}
				if(objectType.equals("timedPlatform")){		
					int duration = getObjectInt(gi, oi, map, "duration");
					gameObjects.add(new TimedPlatform(x,y,w,h,duration,map));
				}
				if(objectType.equals("door")){		
					gameObjects.add(new Door(x, y, w, h, map));
				}
 
				if(objectType.equals("elevator")){
					int range = getObjectInt(gi, oi, map, "range");
					gameObjects.add(new Elevator(x, y, w, h, range, map));
				}
				if(objectType.equals("timedElevator")){

					int range = getObjectInt(gi, oi, map, "range");
					int duration = getObjectInt(gi, oi, map, "duration");

					gameObjects.add(new TimedElevator(x, y, w, h, range, duration, map));
				}

				if(objectType.equals("switch")){
					int tarX = getObjectInt(gi, oi, map, "tx"); // (tx,ty) target location in tiles from tiledmap
					int tarY = getObjectInt(gi, oi, map, "ty");
					int prox = getObjectInt(gi, oi, map, "prox"); // how close you need to be in pxls to switch
					gameObjects.add(new Switch(x,y,w,h,tarX,tarY,prox,map));
				}
				if(objectType.equals("timedSwitch")){
					int tarX = getObjectInt(gi, oi, map, "tx"); // (tx,ty) target location in tiles from tiledmap
					int tarY = getObjectInt(gi, oi, map, "ty");
					int prox = getObjectInt(gi, oi, map, "prox"); // how close you need to be in pxls to switch
					int duration = getObjectInt(gi, oi, map, "duration");
					gameObjects.add(new TimedSwitch(x,y,w,h,tarX,tarY,prox,duration,map));
				}
			}
		}

		// Add swtich targets
		for(GameObject gObj: gameObjects){
			if(gObj instanceof Switch || gObj instanceof TimedSwitch){
				gObj.setTarget(gameObjects);
			}
		}
	}

	private void initializeMeta(TiledMap map){

		int metaIndex = map.getLayerIndex("meta");

		// load the permanently blocked walls
		for(int i = 0; i < map.getWidth(); i++) {
			for(int j = 0; j < map.getHeight(); j++) {

				// Read a Tile
				int tileID = map.getTileId(i, j, metaIndex);
				
				// Get the value of the Property named "blocked"
				String blockedValue = map.getTileProperty(tileID, "blocked", "false");
				if(blockedValue.equals("true")) {
					// keep a list of all the rects of the permanently blocked tiles
					blocks.add(new Rectangle(i * tileSize,j * tileSize, tileSize, tileSize));
				}	

			}
		}
	}

	public String getObjectString(int gi, int oi, TiledMap map, String property){
		String propertyStr =  map.getObjectProperty(gi, oi, property, "nil" );
		return propertyStr;
	}

	public int getObjectInt(int gi, int oi, TiledMap map, String property){
		String propertyStr =  map.getObjectProperty(gi, oi, property, "0" );
		Integer propertyInt = Integer.parseInt(propertyStr);
		return propertyInt;
	}


	public ArrayList<GameObject> getGameObjects(){
		return gameObjects;
	}



	public ArrayList<Rectangle> getBlocks(){
		return blocks;
	}

//	
//	public ArrayList<Enemy> getEnemies(TiledMap map, collisionHandler){
//		
//		ArrayList<Enemy> enemies = new ArrayList<Enemy>(); 
//		
//		int objectGroupCount = map.getObjectGroupCount();
//		for( int gi=0; gi < objectGroupCount; gi++ ) // gi = object group index
//		{
//			int objectCount = map.getObjectCount(gi);
//			for( int oi=0; oi < objectCount; oi++ ) // oi = object index
//			{
//				String objectType =  getObjectString(gi,oi,map,"type");				
//				if(objectType.equals("switch")){
//					// PARSE GAME OBJECT FOR DIMENSION
//					int x = getObjectInt(gi,oi,map,"x");
//					int y = getObjectInt(gi,oi,map,"y");
//					int h = getObjectInt(gi,oi,map,"h");
//					int w = getObjectInt(gi,oi,map,"w");
//
//
//
//					gameObjects.add(new Switch(gi,oi,map));
//				}
//
//			}
//		}
//	}

	
}
