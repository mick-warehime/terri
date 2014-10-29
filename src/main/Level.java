package main;


import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import actors.Enemy;
import etherable.GameObject;


// TODO


public class Level {


	private static int tileSize; // size of a single tile in tilemap
	private int width = 640;
	private int height = 480;
	private int mapX = 0;
	private int mapY = 0;
	private int tol = 15; // number of tiles away from edge
	private int tolX;
	private int tolY;
	private int tileSizeWidth;
	private int tileSizeHeight;
	private TiledMap map;

	private int tileLayerId;
	private CollisionHandler collisionHandler;
	private TileData tileData;
	
	private ArrayList<Enemy> enemies;

	public Level(int x, int y) throws SlickException {

		// initialize the map location
		//tileSize location of top-left corner of the map to blit
		mapX = x;
		mapY = y;

		map = new TiledMap("data/gametiles.tmx");
		

		tileData = new TileData(map);

		// used for drawing (allows the dude to be outside the center of the screen)
		tileSize = map.getTileHeight();
		tolX = tol*tileSize;
		tolY = tol*tileSize;
		tileSizeWidth = width/tileSize;
		tileSizeHeight = height/tileSize;
		tileLayerId = map.getLayerIndex("tiles");
		
		
		this.enemies = new ArrayList<Enemy>();
		collisionHandler = new CollisionHandler(tileData.getBlocks(),tileData.getGameObjects(), enemies);

		

		

	}

	public void addEnemy(Enemy nme){
		enemies.add(nme);
	}
	
	

	public void update(int mouseX, int mouseY){
		for(GameObject gObj: tileData.getGameObjects()){
			gObj.update(mouseX,mouseY);
		}
		
		List<Enemy> toKill = new ArrayList<Enemy>();
		if (enemies != null){
			for (Enemy nme: enemies){
				nme.update();
				if(nme.isDying()){
					toKill.add(nme);
				}	
			}
		}
		
		//Removes dead things from the level lists
		for (Enemy nme : toKill){
			enemies.remove(nme);
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
		map.render(-dX,-dY,tXmin,tYmin,tileSizeWidth,tileSizeHeight,tileLayerId,false);


		for(GameObject gObj: tileData.getGameObjects()){		
			gObj.draw(mapX, mapY, mouseX, mouseY);
		}
		
		for (Enemy nme: enemies){
			nme.render(g,mapX,mapY);
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


	public int getMapX(){return mapX;}
	public int getMapY(){return mapY;}


	public CollisionHandler getCollisionHandler(){
		return collisionHandler;
	}

}
