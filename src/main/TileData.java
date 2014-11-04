
package main;
//import etherable.Platform;
//import etherable.Elevator;
import etherable.DeadlyObject;
import etherable.Door;
import etherable.Elevator;
import etherable.GameObject;
import etherable.Platform;
import etherable.ProgressPoint;
import etherable.Switch;
import etherable.SwitchObject;
import etherable.TimedElevator;
import etherable.TimedPlatform;
import etherable.TimedSwitch;
import etherable.WeightedSwitch;

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
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
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

	public ArrayList<Enemy> getEnemies(){
		return enemies;
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
		parserDict.put("weightedSwitch", WeightedSwitch.class);
		parserDict.put("progressPoint", ProgressPoint.class);

	}


	private void initializeObjects(TiledMap map) throws NumberFormatException, SlickException{
		
		// only load items in the objects layer
		int grpID = map.getObjectLayerIndex("objects");

		int objectCount = map.getObjectCount(grpID);
		for( int oi=0; oi < objectCount; oi++ ) // oi = object index
		{		
			constructFromData(map,grpID,oi);
		}

		// Add swtich targets
		for(GameObject gObj: gameObjects){
			if(gObj instanceof SwitchObject ){
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



	public ArrayList<GameObject> getGameObjects(){
		return gameObjects;
	}



	public ArrayList<Rectangle> getBlocks(){
		return blocks;
	}

	//Make a new instance of a gameobject based on map data
	private void constructFromData(TiledMap map, int gi,int oi) throws SlickException{

		// Define input arguments for constructor
		int x = map.getObjectX(gi,oi)/tileSize;
		int y = map.getObjectY(gi,oi)/tileSize;
		int h = map.getObjectHeight(gi,oi)/tileSize;
		int w = map.getObjectWidth(gi,oi)/tileSize;
		Properties args = map.getObjectProperties(gi,oi);

		//Get object type
		String objectType =  map.getObjectType(gi,oi);	

		//Construct enemies
		if (objectType.equals("enemy")){
			enemies.add(new Enemy(x*tileSize,y*tileSize, null));
		}
		
		//Construct gameObjects
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


