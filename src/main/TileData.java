
package main;
//import etherable.Platform;
//import etherable.Elevator;
import gameobjects.DeadlyObject;
import gameobjects.Door;
import gameobjects.EtherableMovingPlatform;
import gameobjects.MovingPlatform;
import gameobjects.MultiDoor;
import gameobjects.MultiWeightedSwitch;
import gameobjects.EtherObject;
import gameobjects.GameObject;
import gameobjects.Ladder;
import gameobjects.ProgressPoint;
import gameobjects.Switch;
import gameobjects.SwitchObject;
import gameobjects.Teleporter;
import gameobjects.TimedElevator;
import gameobjects.TimedPlatform;
import gameobjects.TimedSwitch;
import gameobjects.Turret;
import gameobjects.WeightedSwitch;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import actors.Actor;
import actors.Enemy;


public class TileData {

	private ArrayList<Rectangle> blocks = new ArrayList<Rectangle>(); 	
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	private ArrayList<Actor> actors = new ArrayList<Actor>();
	
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

	public ArrayList<Actor> getActors(){
		return actors;
	}

	private void initializeParserDictionary() {

		parserDict = new HashMap<String,Class<?>>();

		parserDict.put("platform", EtherObject.class);
		parserDict.put("deadly", DeadlyObject.class);
		parserDict.put("timedPlatform", TimedPlatform.class);
		parserDict.put("door", Door.class);
		parserDict.put("multiDoor", MultiDoor.class);
		parserDict.put("timedElevator", TimedElevator.class);
		parserDict.put("switch", Switch.class);
		parserDict.put("timedSwitch", TimedSwitch.class);
		parserDict.put("weightedSwitch", WeightedSwitch.class);
		parserDict.put("multiWeightedSwitch", MultiWeightedSwitch.class);
		parserDict.put("progressPoint", ProgressPoint.class);
		parserDict.put("teleporter", Teleporter.class);
		parserDict.put("ladder", Ladder.class);
		parserDict.put("turret", Turret.class);
		parserDict.put("movingPlatform", MovingPlatform.class);
		parserDict.put("etherableMovingPlatform", EtherableMovingPlatform.class);

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

		int tileIndex = map.getLayerIndex("tiles");

		// load the permanently blocked walls
		for(int i = 0; i < map.getWidth(); i++) {
			for(int j = 0; j < map.getHeight(); j++) {

				// Read a Tile				

				// Get the value of the Property named "blocked"
				if(!(map.getTileImage(i,j,tileIndex)==null)) {
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
		String name = map.getObjectName(gi, oi);
		
		Properties args = map.getObjectProperties(gi,oi);

		//Get object type
		String objectType =  map.getObjectType(gi,oi);	
 		
		//Construct actors
		if (objectType.equals("enemy")){
			actors.add(new Enemy(x*tileSize,y*tileSize, null));
		}
		
		//Construct gameObjects
		//Define constructor from dictionary, if it's in it
		if (parserDict.containsKey(objectType)){
			Constructor<?>[] test = (parserDict.get(objectType).getConstructors());
			@SuppressWarnings("rawtypes")
			Constructor construct = test[0];

			//		gameObjects.add(new DeadlyObject(x, y, w, h, map,args));
			try {
				GameObject gObj = (GameObject) construct.newInstance(x, y, w, h, name, map,args);
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


