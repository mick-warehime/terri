
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
import etherable.Wall;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;


public class TileData {

	private ArrayList<Rectangle> blocks = new ArrayList<Rectangle>(); 
	//	private ArrayList<Ether> etherObjects = new ArrayList<Ether>();
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
				String objectType =  map.getObjectProperty(gi, oi, "type", "" );				
				
				if(objectType.equals("timedPlatform")){					
					gameObjects.add(new TimedPlatform(gi,oi,map));
				}
				if(objectType.equals("platform")){					
					gameObjects.add(new Platform(gi,oi,map));
				}
				if(objectType.equals("elevator")){					
					gameObjects.add(new Elevator(gi,oi,map));
				}
				if(objectType.equals("timedElevator")){					
					gameObjects.add(new TimedElevator(gi,oi,map));
				}
				
				if(objectType.equals("switch")){
					gameObjects.add(new Switch(gi,oi,map));
				}
				
			}
		}
		
		// Add swtich targets
		for(GameObject gObj: gameObjects){
			if(gObj instanceof Switch){
				gObj.setTarget(gameObjects);
			}
		}

	}


	//  todo instead of finding etherable objects find all objects and have a levelObjects class
	// to return a more general object that can be mutable, etherable, usable, etc.



	private void initializeMeta(TiledMap map){


		// Loop through the Tiles and read their Properties

		// Set here the Layer you want to Read. In your case, it'll be layer 1,
		// since the objects are on the second layer.
		int metaIndex = map.getLayerIndex("meta");


		for(int i = 0; i < map.getWidth(); i++) {
			for(int j = 0; j < map.getHeight(); j++) {

				// Read a Tile
				int tileID = map.getTileId(i, j, metaIndex);

				// Get the value of the Property named "blocked"
				String blockedValue = map.getTileProperty(tileID, "blocked", "false");
				String objectType = map.getTileProperty(tileID, "type","none" );
				// If the value of the Property is "true"...
				if(blockedValue.equals("true")) {
					// keep a list of all the rects of the permanently blocked tiles
					blocks.add(new Rectangle(i * tileSize,j * tileSize, tileSize, tileSize));
				}			
				if(objectType.equals("wall")){
					gameObjects.add(new Wall(i,j,map));
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


}
