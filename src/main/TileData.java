
package main;
//import etherable.Platform;
//import etherable.Elevator;
import etherable.DeadlyObject;
import etherable.Door;
import etherable.Elevator;
import etherable.GameObject;
import etherable.Platform;
import etherable.Switch;
import etherable.TimedElevator;
import etherable.TimedPlatform;
import etherable.TimedSwitch;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import actors.Enemy;


public class TileData {

	private ArrayList<Rectangle> blocks = new ArrayList<Rectangle>(); 	
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

	// This will keep a list of Tiles that are blocked
	private int tileSize;

	//A dictionary of class strings used for GameObject construction.
	private Map<String, Class<?>> parserDict; 

	public TileData(TiledMap map) throws SlickException {
		// TODO Auto-generated constructor stub		
		tileSize = map.getTileHeight();

		initializeParserDictionary();

		initializeMeta(map);
		initializeObjects(map);
	}


	private void initializeParserDictionary() {

		parserDict = new HashMap<String,Class<?>>();

		parserDict.put("platform", Platform.class);
		parserDict.put("deadly", DeadlyObject.class);
		parserDict.put("timedPlatform", TimedPlatform.class);
		parserDict.put("door", Door.class);
		parserDict.put("elevator", Elevator.class);
		parserDict.put("timedElevator", TimedElevator.class);
		parserDict.put("switch", Switch.class);
		parserDict.put("timedSwitch", TimedSwitch.class);








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
				Properties args = map.getObjectProperties(gi,oi);				

				constructFromData(map,gi,oi);
				

				//				if(objectType.equals("platform")){					
				//					gameObjects.add(new Platform(x, y, w, h, map,args));
				//				}
				//				if(objectType.equals("deadly")){
				//					
				//					Constructor<?>[] test = (parserDict.get("deadly").getConstructors());
				//					Constructor construct = test[0];
				//					
				////					gameObjects.add(new DeadlyObject(x, y, w, h, map,args));
				//					try {
				//						gameObjects.add(  (GameObject) construct.newInstance(x, y, w, h, map,args));
				//					} catch (IllegalArgumentException e) {
				//						// TODO Auto-generated catch block
				//						e.printStackTrace();
				//					} catch (InstantiationException e) {
				//						// TODO Auto-generated catch block
				//						e.printStackTrace();
				//					} catch (IllegalAccessException e) {
				//						// TODO Auto-generated catch block
				//						e.printStackTrace();
				//					} catch (InvocationTargetException e) {
				//						// TODO Auto-generated catch block
				//						e.printStackTrace();
				//					}
				//				}
				//				if(objectType.equals("timedPlatform")){	
				//					gameObjects.add(new TimedPlatform(x,y,w,h,map,args));
				//				}
				//				if(objectType.equals("door")){		
				//					gameObjects.add(new Door(x, y, w, h, map,args));
				//				}
				// 
				//				if(objectType.equals("elevator")){
				//					gameObjects.add(new Elevator(x, y, w, h, map, args));
				//				}
				//				if(objectType.equals("timedElevator")){
				//
				//					gameObjects.add(new TimedElevator(x, y, w, h, map,args));
				//				}
				//
				//				if(objectType.equals("switch")){
				////					int tarX = getObjectInt(gi, oi, map, "tx"); // (tx,ty) target location in tiles from tiledmap
				////					int tarY = getObjectInt(gi, oi, map, "ty");
				////					int prox = getObjectInt(gi, oi, map, "prox"); // how close you need to be in pxls to switch
				//					gameObjects.add(new Switch(x,y,w,h,map,args));
				//				}
				//				if(objectType.equals("timedSwitch")){
				////					int tarX = getObjectInt(gi, oi, map, "tx"); // (tx,ty) target location in tiles from tiledmap
				////					int tarY = getObjectInt(gi, oi, map, "ty");
				////					int prox = getObjectInt(gi, oi, map, "prox"); // how close you need to be in pxls to switch
				////					int duration = getObjectInt(gi, oi, map, "duration");
				//					gameObjects.add(new TimedSwitch(x,y,w,h,map,args));
				//				}
			}
		}

		// Add swtich targets
		for(GameObject gObj: gameObjects){
			if(gObj instanceof Switch || gObj instanceof TimedSwitch){
				gObj.setTarget(gameObjects);
			}
		}
	}

	//Create the permanently blocked tiles that don't interact with anything.
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

	//Get a given property from a game object
	private String getObjectString(int gi, int oi, TiledMap map, String property){
		String propertyStr =  map.getObjectProperty(gi, oi, property, "nil" );
		return propertyStr;
	}

	private int getObjectInt(int gi, int oi, TiledMap map, String property){
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

	//Make a new instance of a gameobject based on map data
	private void constructFromData(TiledMap map, int gi,int oi){



		// Define input arguments for constructor
		int x = map.getObjectX(gi,oi)/tileSize;
		int y = map.getObjectY(gi,oi)/tileSize;
		int h = map.getObjectHeight(gi,oi)/tileSize;
		int w = map.getObjectWidth(gi,oi)/tileSize;
		Properties args = map.getObjectProperties(gi,oi);

		//Get object type
		String objectType =  map.getObjectType(gi,oi);	


		//Define constructor from dictionary, if it's in it
		if (parserDict.containsKey(objectType)){
			Constructor<?>[] test = (parserDict.get(objectType).getConstructors());
			Constructor construct = test[0];

			//		gameObjects.add(new DeadlyObject(x, y, w, h, map,args));
			try {
				GameObject gObj = (GameObject) construct.newInstance(x, y, w, h, map,args);
				gameObjects.add(gObj );
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return;
		}

		


	}
}
