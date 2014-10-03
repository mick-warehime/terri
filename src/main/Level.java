package main;


import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;


// TODO
//
//  MAKE A MAP DATA API/MAP LOADER to clean up some of this bullshit

//  Make Map an instance of level???

//  place ether with tiledmap objects


public class Level {


	private int tileSize = 16; // size of a single tile in tilemap
	private int width = 640;
	private int height = 480;
	private int mapX = 0;
	private int mapY = 0;
	private int tolX = 150;
	private int tolY = 150;
	private int tileSizeWidth = width/tileSize;
	private int tileSizeHeight = height/tileSize;
	private TiledMap map;
	private Collide collisionHandler;



	public Level(int x, int y) throws SlickException {

		// initialize the map location
		//tileSize location of top-left corner of the map to blit
		mapX = x;
		mapY = y;
		map = new TiledMap("data/gametiles.tmx");
		collisionHandler = new Collide(map);
	}

	
	public void draw( Graphics g,int x, int y){		


		// min/max sets the submatrix of tiles to draw		
		int tXmin = (int) mapX/tileSize;
		int tYmin = (int) mapY/tileSize;

		// dX/dY are the offsets from the submatrix to the actual screen position
		int dX = mapX - tXmin*tileSize;
		int dY = mapY - tYmin*tileSize;

		// allows the player to get within tolX/tolY of the top/side
		if ((x - tolX) < mapX){mapX = x-tolX;}
		if ((x+tolX-width)>mapX){mapX = x+tolX-width;}
		if ((y - tolY) < mapY){mapY = y-tolY;}
		if ((y+tolY-height) > mapY){mapY = y+tolY-height;}


		// map.render(-mapX,-mapY);
		map.render(-dX,-dY,tXmin,tYmin,tileSizeWidth,tileSizeHeight);

	}

	public int getMapX(){return mapX;}
	public int getMapY(){return mapY;}
	
	public Collide getCollisionHandler(){
		return collisionHandler;
	}
	//This should be moved to a different class, handling
	//only collision detection.

}
