package main;


import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;


// TODO
//
//  MAKE A MAP DATA API/MAP LOADER to clean up some of this bullshit

//  Make Map an instance of level???

//  place ether with tiledmap objects


public class Level {


	private static int tileSize; // size of a single tile in tilemap
	private int width = 640;
	private int height = 480;
	private int mapX = 0;
	private int mapY = 0;
	private int tol = 8; // number of tiles away from edge
	private int tolX;
	private int tolY;
	private int tileSizeWidth;
	private int tileSizeHeight;
	private TiledMap map;
	private Collide collisionHandler;
	private int tileLayerId;

	public Level(int x, int y) throws SlickException {

		// initialize the map location
		//tileSize location of top-left corner of the map to blit
		mapX = x;
		mapY = y;
		
		map = new TiledMap("data/gametiles.tmx");
		collisionHandler = new Collide(map);
		tileLayerId = map.getLayerIndex("tiles");
		
		tileSize = map.getTileHeight();
		tolX = tol*tileSize;
		tolY = tol*tileSize;
		tileSizeWidth = width/tileSize;
		tileSizeHeight = height/tileSize;
		
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

		// see if we are close to the edge of a map
		mapXCheck();
		mapYCheck();		
		
		// map.render(-mapX,-mapY);
		map.render(-dX,-dY,tXmin,tYmin,tileSizeWidth,tileSizeHeight,tileLayerId,false);

		
		//     drawEther(g);
		for(Ether s:collisionHandler.getEtherObjects()){
			s.draw(mapX,mapY,mouseX,mouseY);
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
	
	
//	private void drawEther(Graphics g){
//		// loop over the ether tiles and draw the ether/solid tiles where necessary
//		for(int i = 0; i < map.getWidth(); i++){
//			for(int j = 0; j < map.getHeight(); j++){
//				
//				if(collisionHandler.isEther(i,j)){
//					etherSprite.draw(i*tileSize-mapX,j*tileSize-mapY);
//				}
//				if(collisionHandler.isSolid(i,j)){
//					solidSprite.draw(i*tileSize-mapX,j*tileSize-mapY);
//				}
////				
//			}
//		}
//	}
	
	public int getMapX(){return mapX;}
	public int getMapY(){return mapY;}
	
	public Collide getCollisionHandler(){
		return collisionHandler;
	}
}
