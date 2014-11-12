package main;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

import actors.Actor;
import actors.Enemy;
import commands.CommandProvider;
import gameobjects.Door;
import gameobjects.EtherObject;
import gameobjects.Etherable;
import gameobjects.GameObject;
import gameobjects.Interactive;
import gameobjects.InteractiveCollideable;


public class CollisionHandler implements CommandProvider {

	private ArrayList<Rectangle> blocks;
	private ArrayList<GameObject> gameObjects;
	private ArrayList<Actor> actors;

	//	private ArrayList<GameObject> gameObjects2;
	private Rectangle playerRect;

	// Objects that do something on collision
	private ArrayList <InteractiveCollideable> interactiveGameObjects;

	public CollisionHandler(ArrayList<Rectangle> blockedList){
		this.blocks = blockedList;


	}


	public void receiveObjects(ArrayList<GameObject> gameObjects, ArrayList<Actor> actors){

		this.gameObjects = gameObjects;
		this.actors = actors;


		populateInteractiveCollideables();

	}

	private void populateInteractiveCollideables() {
		interactiveGameObjects = new ArrayList<InteractiveCollideable>();

		for (GameObject gObj: gameObjects){
			if (gObj instanceof InteractiveCollideable){
				interactiveGameObjects.add((InteractiveCollideable) gObj);
			}
		}

		for (Actor actor: actors){
			if (actor instanceof InteractiveCollideable){
				interactiveGameObjects.add((InteractiveCollideable) actor);
			}
		}




	}


	public void addPlayerRect(Rectangle playerRect){
		this.playerRect = playerRect;		
	}





	public Etherable isAtEtherObject(int x, int y){

		for(GameObject gObj: gameObjects){
			if(gObj instanceof Etherable){
				if(gObj.getRect().contains(x,y)){
					return (Etherable) gObj;
				}
			}
		}

		return null;
	}

	public Etherable isAtEtherEnemy(int x, int y){

		for(GameObject gObj: gameObjects){
			if(gObj instanceof Etherable){
				if(gObj.getRect().contains(x,y)){
					return (Etherable) gObj;
				}
			}
		}
		for (Actor nme: actors){
			if(nme instanceof Etherable){
				if(nme.getRect().contains(x,y)){
					return (Etherable) nme;
				}
			}
		}

		return null;
	}



	//Returns a list of interactive game objects near the player
	public ArrayList<GameObject> interactiveObjectsNearRect(Rectangle rect){

		ArrayList<GameObject> output = new ArrayList<GameObject>();

		for(GameObject gObj: gameObjects){
			if (gObj instanceof Interactive){
				if (gObj.isNear(rect)){
					output.add(gObj);
				}

			}
		}


		return output;
	}



	public boolean canPlaceEtherAt(EtherObject etherObject){
		boolean answer = !isCollided((GameObject) etherObject);
		answer = answer && !playerRect.intersects(etherObject.getRect());
		answer = answer && !isCollidedWithActor(etherObject.getRect());
		answer = answer && !isCollidedWithDoor(etherObject.getRect());
		return answer;
	}

	public boolean canPlaceEtherAt(Actor actor){
		boolean answer = !isCollided(actor.getRect());
		answer = answer && !playerRect.intersects(actor.getRect());
		answer = answer && !isCollidedWithActor(actor);
		answer = answer && !isCollidedWithDoor(actor.getRect());
		return answer;
	}


	//Checks for collisions with blocks and game Objects
	public boolean isCollided(Rectangle rect){	
		boolean answer = false;
		//	check if collided with permanent solid blocks	
		answer = answer || isCollidedWithBlocks(rect);
		// check if collided with solid etherable Objects
		answer = answer || isCollidedWithObjects(rect);

		return answer;
	}

	public boolean isCollidedWithBlocks(Rectangle rect){
		for(Rectangle r: blocks ){
			if(rect.intersects(r)){
				return true;
			}	
		}
		return false;
	}

	public boolean isCollidedWithObjects(Rectangle rect){
		for(GameObject gObj: gameObjects){
			if(gObj.canCollide()){
				if(rect.intersects(gObj.getRect())){
					return true;
				}

			}

		}
		return false;
	}

	public boolean isCollidedWithDoor(Rectangle rect){
		for(GameObject gObj: gameObjects){
			if(gObj instanceof Door){
				if(rect.intersects(gObj.getRect())){
					return true;
				}

			}

		}
		return false;
	}


	public boolean isCollided(GameObject gameObject){
		boolean answer = false;

		// check if collided with permanent solid blocks	
		answer = answer || isCollidedWithBlocks(gameObject);

		// check if collided with gameOjbects that arent the input variable
		answer = answer || isCollidedWithObjects(gameObject);

		return answer;
	}

	public boolean isCollidedWithBlocks(GameObject gameObject){
		for(Rectangle r: blocks ){
			if(r.intersects(gameObject.getRect())){
				return true;
			}	
		}
		return false;
	}

	public boolean isCollidedWithObjects(GameObject gameObject){
		// check if collided with solid etherable Objects
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

	public ArrayList<Command> getCommands(){
		return resolveInteractiveCollisions(playerRect, "Player");
	}

	public boolean lineOfSightCollision(GameObject testObject){
		return lineOfSightCollision((Etherable) testObject);
	}

	//Returns if the line of sight from the player to an EtherObject
	// is collided with any game objects
	public boolean lineOfSightCollision(Etherable testObject){

		//Make a line from centers of player and object
		float playerX = playerRect.getCenterX();
		float playerY = playerRect.getCenterY();
		float objectX = testObject.getRect().getCenterX();
		float objectY = testObject.getRect().getCenterY();

		Line line = new Line(playerX, playerY, objectX, objectY);

		//Check if collideable Game objects are intersecting 
		// this line, other than the one from eObj
		for(GameObject gObj: gameObjects){
			if(gObj != testObject && gObj.canCollide()){
				if(line.intersects(gObj.getRect())){
					return true;
				}
			}
		}

		for (Actor nme: actors){
			if(nme.canCollide() && nme != testObject){
				if(line.intersects(nme.getRect())){
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

	public boolean isCollidedWithActor(Rectangle rect){
		for (Actor nme: actors){
			if(nme.canCollide()){
				if(nme.getRect().intersects(rect)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCollidedWithActor(Actor actor){
		for (Actor nme: actors){
			if(nme.canCollide() && nme != actor){
				if(nme.getRect().intersects(actor.getRect())){
					return true;
				}
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
				output.addAll(interObj.onCollisionBroadcast(collidingObjectClass));
			}
		}

		//		for (Actor nme: actors){
		//			if (slightlyBiggerRect.intersects(nme.getRect())){
		//				nme.onCollisionDo(collidingObjectClass);
		//				output.addAll(nme.onCollisionBroadcast(collidingObjectClass));
		//			}
		//		}



		return output;
	}


	public float getPlayerCenterX(){
		return playerRect.getX()+playerRect.getWidth()/2;
	}
	public float getPlayerCenterY(){
		return playerRect.getY()+playerRect.getHeight()/2;
	}



}
