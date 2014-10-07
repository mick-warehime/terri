
package main;
import etherable.Ether;
import etherable.Platform;
import etherable.Elevator;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;


public class TileData {

	private ArrayList<Rectangle> blocks = new ArrayList<Rectangle>(); 
	private ArrayList<Ether> etherObjects = new ArrayList<Ether>(); 

	// This will keep a list of Tiles that are blocked
	private int tileSize;

	public TileData(TiledMap map) throws SlickException {
		// TODO Auto-generated constructor stub		
		tileSize = map.getTileHeight();
		initializeMeta(map);
	}


	//  todo instead of finding etherable objects find all objects and have a levelObjects class
	// to return a more general object that can be mutable, etherable, usable, etc.



	private void initializeMeta(TiledMap map){


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

					// keep a list of all the rects of the permanently blocked tiles
					blocks.add(new Rectangle(i * tileSize,j * tileSize, tileSize, tileSize));
				}
				if(etherValue.equals("true")) {		
					// a list of all the etherable
					if(etherType.equals("platform")){
						etherObjects.add(new Platform(i,j,false,map,etherIndex));
					}
					if(etherType.equals("elevator")){
						etherObjects.add(new Elevator(i,j,false,map,etherIndex));
					}
				}
			}
		}	
	}

	public ArrayList<Ether> getEtherObjects(){
		return etherObjects;
	}

	public ArrayList<Rectangle> getBlocks(){
		return blocks;
	}


}
