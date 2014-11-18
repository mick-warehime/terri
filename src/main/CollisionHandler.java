package main;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import actors.Actor;
import actors.Player;
import commands.CommandProvider;
import gameobjects.Door;
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


	public void receiveObjects(ArrayList<GameObject> gameObjects, ArrayList<Actor> actors, ArrayList<InteractiveCollideable> interactiveCollideables){

		this.gameObjects = gameObjects;
		this.actors = actors;
		this.interactiveGameObjects = interactiveCollideables;

		//		populateInteractiveCollideables();

	}

	//	private void populateInteractiveCollideables() {
	//		interactiveGameObjects = new ArrayList<InteractiveCollideable>();
	//
	//		for (GameObject gObj: gameObjects){
	//			if (gObj instanceof InteractiveCollideable){
	//				interactiveGameObjects.add((InteractiveCollideable) gObj);
	//			}
	//		}
	//
	//		for (Actor actor: actors){
	//			if (actor instanceof InteractiveCollideable){
	//				interactiveGameObjects.add((InteractiveCollideable) actor);
	//			}
	//		}
	//
	//
	//	}


	public void addPlayerRect(Rectangle playerRect){
		this.playerRect = playerRect;		
	}





	public Etherable isAtEtherObject(int x, int y){

		for(GameObject gObj: gameObjects){
			if(gObj instanceof Etherable){
				if(gObj.getShape().contains(x,y)){
					return (Etherable) gObj;
				}
			}
		}

		return null;
	}

	public Etherable isAtEtherEnemy(int x, int y){

		for(GameObject gObj: gameObjects){
			if(gObj instanceof Etherable){
				if(gObj.getShape().contains(x,y)){
					return (Etherable) gObj;
				}
			}
		}
		for (Actor nme: actors){
			if(nme instanceof Etherable){
				if(nme.getShape().contains(x,y)){
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



	public boolean canPlaceEtherAt(Shape shape){
		boolean answer = !isCollided(shape);
		answer = answer && !playerRect.intersects(shape);
		answer = answer && !isCollidedWithActor(shape);
		answer = answer && !isCollidedWithDoor(shape);
		return answer;
	}


	//Checks for collisions with blocks and game Objects
	public boolean isCollided(Shape shape){	
		boolean answer = false;
		//	check if collided with permanent solid blocks	
		answer = answer || isCollidedWithBlocks(shape);
		// check if collided with solid etherable Objects
		answer = answer || isCollidedWithObjects(shape);

		return answer;
	}

	public boolean isCollidedWithBlocks(Shape shape){
		for(Rectangle r: blocks ){
			if(shape.intersects(r)){
				return true;
			}	
		}
		return false;
	}

	public boolean isCollidedWithDoor(Shape shape){
		for(GameObject gObj: gameObjects){
			if(gObj instanceof Door){
				if(shape.intersects(gObj.getShape())){
					return true;
				}

			}

		}
		return false;
	}

	public boolean isCollidedWithActor(Shape shape){
		for (Actor nme: actors){
			if(nme.canCollide()){
				if(nme.getShape().intersects(shape)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isCollidedWithObjects(Shape shape){
		// check if collided with solid etherable Objects
		for(GameObject gObj: gameObjects){
			// don't check with its own shape and dont check with objects that are currently being held
			if(gObj.getShape() != shape){
				if(gObj.canCollide()){
					if(shape.intersects(gObj.getShape())){
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isCollidedWithPlayer(Shape etherRect){
		return playerRect.intersects(etherRect);
	}


	public ArrayList<Command> getCommands(){
		return resolveInteractiveCollisions(playerRect, Player.class);
	}


	
	//Checks if the straight line between two shapes intersects
	// any other collideable shape
	public boolean lineOfSightCollision(Shape shape, Shape shape2) {


		//Make a line from centers of player and object
		float x1 = shape.getCenterX();
		float y1 = shape.getCenterY();
		float x2 = shape2.getCenterX();
		float y2 = shape2.getCenterY();

		Line line = new Line(x1, y1, x2, y2);

		//Check if collideable Game objects are intersecting 
		// this line, other than the inputs
		for(GameObject gObj: gameObjects){
			if(gObj.canCollide()){
				if(gObj.getShape() != shape && gObj.getShape() != shape2 ){
					if(line.intersects(gObj.getShape())){
						return true;
					}
				}
			}
		}

		for (Actor actor: actors){
			if(actor.canCollide()){
				if(actor.getShape() != shape && actor.getShape() != shape2 ){
					if(line.intersects(actor.getShape())){
						return true;
					}
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



	//Returns if the line of sight from the player to an EtherObject
	// is collided with any game objects
	public boolean lineOfSightCollisionToPlayer(Shape shape){
		return lineOfSightCollision(shape,playerRect);
	}


	//Checks a shape for collisions with interactive collideables
	// outputs a list of commands for an actor with the shape to do,
	// and does the interactive's inherent collision command
	// For collisions to be class specific, we pass in a 
	// collidingObjectClass.
	public ArrayList <Command> resolveInteractiveCollisions(Rectangle rect, Class collidingObjectClass ){
		ArrayList<Command> output = new  ArrayList <Command>();

		//Make a slightly bigger rectangle because physics don't 
		// allow you to actually move into another object
		int proximity = 1;
		Rectangle slightlyBiggerRect = new Rectangle(rect.getX()-proximity,rect.getY()-proximity,rect.getWidth()+2*proximity,rect.getHeight()+2*proximity);

		//
		for (InteractiveCollideable interObj : interactiveGameObjects){

			if (slightlyBiggerRect.intersects(interObj.getShape())){
				interObj.onCollisionDo(collidingObjectClass, rect);
				output.addAll(interObj.onCollisionBroadcast(collidingObjectClass, rect));
			}
		}

		return output;
	}


	public float getPlayerCenterX(){
		return playerRect.getX()+playerRect.getWidth()/2;
	}
	public float getPlayerCenterY(){
		return playerRect.getY()+playerRect.getHeight()/2;
	}





}
