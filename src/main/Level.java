package main;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import actors.Actor;
import actors.Enemy;
import actors.EtherEnemy;
import gameobjects.Etherable;
import gameobjects.GameObject;
import gameobjects.InteractiveCollideable;
import gameobjects.ObjectCreator;
import gameobjects.ParticleBeam;
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
	private ArrayList<Actor> actors;
	private ArrayList<ObjectCreator> creators;
	private ArrayList<InteractiveCollideable> collideables;
	private int[] mousePos;

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
		this.actors = tileData.getActors();


		//Add object creators 
		this.creators = new ArrayList<ObjectCreator>();
		for (GameObject gObj:gameObjects){
			if(gObj instanceof ObjectCreator){
				creators.add((ObjectCreator) gObj);
			}
		}
		for (Actor actor:actors){
			if(actor instanceof ObjectCreator){
				creators.add((ObjectCreator) actor);
			}
		}

		//Add interactive Collideables
		this.collideables = new ArrayList<InteractiveCollideable>();
		for (GameObject gObj:gameObjects){
			if(gObj instanceof InteractiveCollideable){
				collideables.add((InteractiveCollideable) gObj);
			}
		}
		for (Actor actor:actors){
			if(actor instanceof InteractiveCollideable){
				collideables.add((InteractiveCollideable) actor);
			}
		}

		incorporateCollisionHandler(); 



	}


	private void incorporateCollisionHandler() throws SlickException{

		//Give the objects to the collisionHandler
		collisionHandler.receiveObjects(gameObjects, actors, collideables);

		//Give the CollisionHandler to actors and gameObjects

		for(GameObject gObj: gameObjects){
			gObj.setCollisionHandler(collisionHandler);
		}

		for (Actor nme: actors){
			nme.incorporateCollisionHandler(collisionHandler);
		}

	};






	private void removeFromList(Object obj, ArrayList<?> list){
		
		if (list.contains(obj)){
			list.remove(obj);
		}
	}

	public void update() throws SlickException{
		//Update game Objects and remove 'dead' ones
		for(Iterator<GameObject> iterator = gameObjects.iterator(); iterator.hasNext(); ){
			GameObject gObj = iterator.next();
			gObj.update();

			if (gObj.isDying()){
				iterator.remove();
				removeFromList(gObj,creators);
				removeFromList(gObj,collideables);
			}
		}



		//Update actors and remove dead ones
		for (Iterator<Actor> iterator = actors.iterator(); iterator.hasNext();) {
			Actor nme = iterator.next();

			nme.update();
			if (nme.isDying()) {
				// Remove the current element from the iterator and the list.
				iterator.remove();
				removeFromList(nme,creators);
				removeFromList(nme,collideables);
			}
		}

		//Cycle through object creators and add their outputs 
		for (ObjectCreator creator : creators){
			if(creator.hasObject()){
				Object obj = creator.getObject();
				
				incorporateNewObject(obj);	
				

			}

		}
		

	}

	//Add a new object to lists and pass it necessary objects from level
	private void incorporateNewObject(Object obj){
		
		if(obj instanceof GameObject){
			gameObjects.add((GameObject)obj);
			((GameObject) obj).setCollisionHandler(collisionHandler);
		}
		if(obj instanceof Actor){
			actors.add((Actor)obj);
			((Actor) obj).incorporateCollisionHandler(collisionHandler);
		}
		if(obj instanceof InteractiveCollideable){
			collideables.add((InteractiveCollideable)obj);
		}
		
		if(obj instanceof Etherable){
			((Etherable) obj).setMousePosition(mousePos);
		}
	}

	public void draw(Graphics g,int x, int y){		

		
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
			gObj.render(mapX, mapY);
		}

		for (Actor nme: actors){
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


	public void setMousePosition(int[] mousePos) {
		this.mousePos = mousePos;

		//Give the mouse position to etherables
		for (GameObject obj: gameObjects){
			if(obj instanceof Etherable){
				((Etherable)obj).setMousePosition(mousePos);
			}
		}
		//Give the mouse position to etherables
		for (Actor actor: actors){
			if(actor instanceof Etherable){
				((Etherable)actor).setMousePosition(mousePos);
			}
		}


	}

}
