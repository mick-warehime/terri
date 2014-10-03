package main;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class Collide {

	private ArrayList<Rectangle> blocks = new ArrayList<Rectangle>(); 

	// This will keep a list of Tiles that are blocked
	private boolean blocked[][];
	private boolean ether[][];
	private boolean solid[][];
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
		ether = new boolean[map.getWidth()][map.getHeight()];
		solid = new boolean[map.getWidth()][map.getHeight()];

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
				String etherValue = map.getTileProperty(tileID, "ether", "false");

				// If the value of the Property is "true"...
				if(blockedValue.equals("true")) {

					// We set that index of the TileMap as blocked
					blocked[i][j] = true;

					// And create the collision Rectangle
					blocks.add(new Rectangle(i * tileSize,j * tileSize, tileSize, tileSize));
				}
				if(etherValue.equals("true")) {
					// We set that index of the TileMap as blocked
					solid[i][j] = true;
					blocks.add(new Rectangle(i * tileSize,j * tileSize, tileSize, tileSize));
				}
			}
		}
		
	}



	public void toggleEther(int i, int j){
		ether[i][j] = !ether[i][j];
		solid[i][j] = !solid[i][j];

		if(ether[i][j]){

			for(int k = 0; k < blocks.size(); k++){
				Rectangle r = blocks.get(k);
				if(r.getMinX()/tileSize == i && r.getMinY()/tileSize == j){
					blocks.remove(k);
					
//					System.out.println(i+"   "+j);
				}
			}						
		}
		if(solid[i][j]){

			blocks.add(new Rectangle(i * tileSize,j * tileSize, tileSize, tileSize));
		}						
	}




	public boolean isEther(int i, int j){
		return ether[i][j];
	}
	public boolean isSolid(int i, int j){
		return solid[i][j];
	}

	public boolean isCollided(Rectangle heroRect){	
		//		System.out.println(getRectStr(playerRect));
		//		System.out.println(getRectStr(heroRect));
		for(Rectangle r: blocks ){
			if(heroRect.intersects(r)){
				return true;
			}	
		}
		return false;
	}

	public static String getStr(float num){
		return String.valueOf(num);
	}
	public static String getRectStr(Rectangle rect){
		return "[xmin: "+getStr(rect.getX())+" ymin: "+getStr(rect.getY())+" xmax: "+getStr(rect.getWidth()+rect.getX())+" ymax: "+getStr(rect.getY()+rect.getHeight())+"]";
	}
}
