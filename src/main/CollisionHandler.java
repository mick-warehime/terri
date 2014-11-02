package main;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

import actors.Enemy;
import commands.CommandProvider;
import etherable.Elevator;
import etherable.EtherObject;
import etherable.GameObject;
import etherable.Interactive;
import etherable.InteractiveCollideable;


public class CollisionHandler implements CommandProvider {


	private ArrayList<Rectangle> blocks;
	private ArrayList<GameObject> gameObjects;
	private ArrayList<Enemy> enemies;
	//	private ArrayList<GameObject> gameObjects2;
	private Rectangle playerRect;

	// Objects that do something on collision
	private ArrayList <InteractiveCollideable> interactiveGameObjects;

	public CollisionHandler(ArrayList<Rectangle> blockedList, ArrayList<GameObject> gameObjects, ArrayList<Enemy> enemies){
		this.blocks = blockedList;
		this.gameObjects = gameObjects;
		this.enemies = enemies;

		// add the collisionHandler to the ether objects that need it

		for(GameObject gObj: gameObjects){
			
				gObj.setCollisionHandler(this);
			
				
		}
		
		populateInteractiveObjects();
		

	}

	
	private void populateInteractiveObjects() {
		interactiveGameObjects = new ArrayList<InteractiveCollideable>();
		
		for (GameObject gObj: gameObjects){
			if (gObj instanceof InteractiveCollideable){
				interactiveGameObjects.add((InteractiveCollideable) gObj);
			}
		}
		
		
	}


	public void addPlayerRect(Rectangle playerRect){
		this.playerRect = playerRect;		
	}


	//Checks for collisions with blocks and game Objects
	public boolean isCollided(Rectangle rect){	
		//	check if collided with permanent solid blocks	
		for(Rectangle r: blocks ){
			if(rect.intersects(r)){
				return true;
			}	
		}
		// check if collided with solid etherable Objects
		for(GameObject gObj: gameObjects){
			if(gObj.canCollide()){
				if(rect.intersects(gObj.getRect())){
					return true;
				}
			}
		}

		return false;
	}





	public EtherObject isAtEtherObject(int x, int y){

		for(GameObject gObj: gameObjects){
			if(gObj instanceof EtherObject){
				if(gObj.getRect().contains(x,y)){
					return (EtherObject) gObj;
				}
			}
		}

		return null;
	}

	
	//Returns a list of interactive game objects near the player
	public ArrayList<GameObject> interactivesNearPlayer(){

		ArrayList<GameObject> output = new ArrayList<GameObject>();
		
		for(GameObject gObj: gameObjects){
			if (gObj instanceof Interactive){
				if (gObj.isNear(playerRect)){
					output.add(gObj);
				}
				
			}
		}
		

		return output;
	}
	


	public boolean canPlaceEtherAt(EtherObject etherObject){
				
		if (isCollided(etherObject)){return false;}

		if(playerRect.intersects(etherObject.getRect())){
			return false;
		}
		
		//Check for collisions with actors
		if(collidedWithEnemy(etherObject.getRect())){
			return false;
		}
		
		return true;
	}

	public boolean isCollided(GameObject gameObject){
		//		check if collided with permanent solid blocks	
		for(Rectangle r: blocks ){
			if(r.intersects(gameObject.getRect())){
				return true;
			}	
		}
		// check if collided with solid gameObjects
		for(GameObject gObj: gameObjects){
			// don't check with its own rect and dont check with objects that are currently being held
			if(gObj != gameObject){
				if(gObj.canCollide()){
					if(gameObject.getRect().intersects(gObj.getRect())){
						return true;
					}
				}
			}
		}

		return false;
	}


	public boolean isCollidedWithPlayer(Rectangle rect){

		return playerRect.intersects(rect);
	}
	
	public boolean isCollidedWithPlayer(GameObject gObj){

		return playerRect.intersects(gObj.getRect());
	}

//	public void addToCommandStack(Command cmd){
//		collisionCommandStack.add(cmd);
//
//	}

	public ArrayList<Command> getCommands(){
//		@SuppressWarnings("unchecked")
//		ArrayList<Command> answer = (ArrayList<Command>) collisionCommandStack.clone();
//		collisionCommandStack.clear();
//		return answer;
		return resolveInteractiveCollisions(playerRect, "Player");
	}
	
	//Returns if the line of sight from the player to an EtherObject
	// is collided with any game objects
	public boolean lineOfSightCollision(EtherObject eObj){
		
		//Make a line from centers of player and object
		float playerX = playerRect.getCenterX();
		float playerY = playerRect.getCenterY();
		float objectX = eObj.getRect().getCenterX();
		float objectY = eObj.getRect().getCenterY();
		
		Line line = new Line(playerX, playerY, objectX, objectY);
		
		//Check if collideable Game objects are intersecting 
		// this line, other than the one from eObj
		for(GameObject gObj: gameObjects){
			if(gObj != eObj && gObj.canCollide()){
				if(line.intersects(gObj.getRect())){
					return true;
				}
			}
		}
		//Also check the basic game tiles
		for(Rectangle block :blocks){
			if(line.intersects(block)){
				return true;
			}
		}
				
		return false;
	}
	
	private boolean collidedWithEnemy(Rectangle rect){
		for (Enemy nme: enemies){
			if(nme.getRect().intersects(rect)){
				return true;
			}
		}
		return false;
	}

	
	
	//Checks a rect for collisions with interactive collideables
	// outputs a list of commands for an actor with the rect to do,
	// and does the interactive's inherent collision command
	// For collisions to be class specific, we pass in a 
	// collidingObjectClass.
	public ArrayList <Command> resolveInteractiveCollisions(Rectangle rect, String collidingObjectClass ){
		ArrayList<Command> output = new  ArrayList <Command>();
		
		//Make a slightly bigger rectangle because physics don't 
		// allow you to actually move into another object
		int proximity = 1;
		Rectangle slightlyBiggerRect = new Rectangle(rect.getX()-proximity,rect.getY()-proximity,rect.getWidth()+2*proximity,rect.getHeight()+2*proximity);
		
		//
		for (InteractiveCollideable interObj : interactiveGameObjects){
			if (slightlyBiggerRect.intersects(interObj.getRect())){
				interObj.onCollisionDo(collidingObjectClass);
				output.add(interObj.onCollisionBroadcast(collidingObjectClass));
			}
		}
		
		for (Enemy nme: enemies){
			if (slightlyBiggerRect.intersects(nme.getRect())){
				nme.onCollisionDo(collidingObjectClass);
				output.add(nme.onCollisionBroadcast(collidingObjectClass));
			}
		}
		
		
		
		return output;
	}


}
