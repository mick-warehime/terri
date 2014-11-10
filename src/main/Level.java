package main;


import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import actors.Enemy;
import gameobjects.GameObject;
import gameobjects.ProgressPoint;


// TODO


public class Level {


	private static int tileSize; // size of a single tile in tilemap
	private int width = 640;
	private int height = 480;
	private int mapX = 0;
	private int mapY = 0;
	private int tol = 18; // number of tiles away from edge
	private int tolX;
	private int tolY;
	private ProgressPoint progress;
	private int mapWidthInTiles;
	private int mapHeightInTiles;
	private TiledMap map;

	private int tileLayerId;
	private CollisionHandler collisionHandler;
	
//	private TileData tileData;
	private ArrayList<GameObject> gameObjects;
	private ArrayList<Enemy> enemies;

	public Level(int levelNumber) throws SlickException {

		// load map
		String fileData = "data/Level" + levelNumber + ".tmx";
		map = new TiledMap(fileData);
		TileData tileData = new TileData(map);

		// used for drawing (allows the dude to be outside the center of the screen)
		tileSize = map.getTileHeight();
		tolX = tol*tileSize;
		tolY = tol*tileSize;
		mapWidthInTiles = map.getWidth();
		mapHeightInTiles = map.getHeight();
//		width = mapWidthInTiles*map.getTileWidth();
//		height = mapHeightInTiles*map.getTileHeight;
		tileLayerId = map.getLayerIndex("tiles");

		
		//Creates the collisionHandler with just game blocks
		collisionHandler = new CollisionHandler(tileData.getBlocks());
		this.gameObjects = tileData.getGameObjects();
		this.enemies = tileData.getEnemies();
		incorporateCollisionHandler(); 
	

	}
	

	private void incorporateCollisionHandler() throws SlickException{
		
		//Give the objects to the collisionHandler
		collisionHandler.receiveObjects(gameObjects, enemies);
		
		//Give the CollisionHandler to enemies and gameObjects
		
		for(GameObject gObj: gameObjects){
			
			gObj.setCollisionHandler(collisionHandler);
		
			
		}
		
		
		for (Enemy nme: enemies){
			nme.incorporateCollisionHandler(collisionHandler);
			
		}
	};




	



	public void update(int mouseX, int mouseY){
		for(GameObject gObj: gameObjects){
			gObj.update(mouseX,mouseY);
		}

		//Update enemies and remove dead ones
		for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
		    Enemy nme = iterator.next();
		    nme.update();
		    
		    if (nme.isDying()) {
		        // Remove the current element from the iterator and the list.
		        iterator.remove();
		    }
		}			
	

	}




	public void draw( Graphics g,int x, int y, int mouseX, int mouseY){		


		// min/max sets the submatrix of tiles to draw		
		int tXmin = (int) mapX/tileSize;
		int tYmin = (int) mapY/tileSize;

		// dX/dY are the offsets from the submatrix to the actual screen position
		int dX = mapX - tXmin*tileSize;
		int dY = mapY - tYmin*tileSize;

		// allows the player to get within tolX/tolY of the top/side
		if (mapX > (x - tolX) ){mapX = x-tolX;}
		if (mapX < (x+tolX-width)){mapX = x+tolX-width;}
		if (mapY > (y - tolY) ){mapY = y-tolY;}
		if (mapY < (y+tolY-height)){mapY = y+tolY-height;}

		// see if we are close to the edge of a map inwhich case dont let mapx<0 or mapx>size of map in pixels
		mapXCheck();
		mapYCheck();		

		// map.render(-mapX,-mapY);
		map.render(-dX,-dY,tXmin,tYmin,mapWidthInTiles,mapHeightInTiles+1,tileLayerId,false);


		for(GameObject gObj: gameObjects){		
			gObj.draw(mapX, mapY, mouseX, mouseY);
		}

		for (Enemy nme: enemies){
			nme.render(mapX,mapY);
		}

	}

	private void mapXCheck(){
		if(mapX<0){
			mapX = 0;
			tolX = tileSize;			
		}else if(mapX>map.getWidth()*tileSize-width){
			mapX = map.getWidth()*tileSize-width;
			tolX = tileSize;
		}else{
			tolX = tol*tileSize;
		}
	}
	private void mapYCheck(){

		if(mapY<0){
			mapY = 0;
			tolY = tileSize;			
		}
		else if(mapY>map.getHeight()*tileSize-height){
			mapY = map.getHeight()*tileSize-height;
			tolY = tileSize;
		}
		else{
			tolY = tol*tileSize;
		}
	}

	public ProgressPoint getProgressPoint(){

		int curIndex = -1;
		ProgressPoint pPoint = null;
		
		// loop over all progress points in the game
		for(GameObject gObj: gameObjects){
			// see if any progress points are active
			if(gObj instanceof ProgressPoint){
				// make sure the progress point has been activated
				if(((ProgressPoint) gObj).isActive()){
					// make sure you only take the farthest progress point
					if( ((ProgressPoint) gObj).getIndex() > curIndex){

						curIndex = ((ProgressPoint) gObj).getIndex();
						pPoint = (ProgressPoint) gObj;
					}
				}
			}
		}

		return pPoint;
	}

	public int getProgressX(){
		return progress.getPX();
	}
	
	public int getProgressY(){
		return progress.getPY();
	}
	
	public void setProgressPoint(ProgressPoint progress){
		this.progress = progress;
	}


	public TiledMap getMap(){
		return map;
	}

	public int getMapX(){return mapX;}
	public int getMapY(){return mapY;}


	public CollisionHandler getCollisionHandler(){
		return collisionHandler;
	}

}
