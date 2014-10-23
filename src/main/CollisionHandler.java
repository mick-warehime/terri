package main;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

import etherable.Elevator;
import etherable.EtherObject;
import etherable.GameObject;
import etherable.Interactive;


public class CollisionHandler {


	private ArrayList<Rectangle> blocks;
	private ArrayList<GameObject> gameObjects;
	//	private ArrayList<GameObject> gameObjects2;
	private Rectangle playerRect;
	//Commands sent to the player if a collision
	// occurs
	private ArrayList<Command> collisionCommandStack = new ArrayList<Command>();


	public CollisionHandler(ArrayList<Rectangle> blockedList, ArrayList<GameObject> gameObjects){
		this.blocks = blockedList;
		this.gameObjects = gameObjects;

		// add the collisionHandler to the ether objects that need it

		for(GameObject gObj: gameObjects){
			if(gObj instanceof Elevator){
				gObj.setCollisionHandler(this);
			}
		}


	}


	public void addPlayerRect(Rectangle playerRect){
		this.playerRect = playerRect;		
	}


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
	


	public boolean canPlaceEtherAt(GameObject gameObject){

		if (isCollidedWithNonPlayer(gameObject)){return false;}

		if(playerRect.intersects(gameObject.getRect())){
			return false;
		}
		return true;
	}

	public boolean isCollidedWithNonPlayer(GameObject gameObject){
		//		check if collided with permanent solid blocks	
		for(Rectangle r: blocks ){
			if(r.intersects(gameObject.getRect())){
				return true;
			}	
		}
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

	public void addToCommandStack(Command cmd){
		collisionCommandStack.add(cmd);

	}

	public ArrayList<Command> popCollisionCommands(){
		@SuppressWarnings("unchecked")
		ArrayList<Command> answer = (ArrayList<Command>) collisionCommandStack.clone();
		collisionCommandStack.clear();
		return answer;
	}
	
	//Returns if the line of sight from the player to an EtherObject
	// is collided with any game objects
	public boolean lineOfSightCollision(EtherObject eObj){
		
		//Make a line from centers of player and object
		float playerX = playerRect.getCenterX();
		float playerY = playerRect.getCenterY();
		float objectX = eObj.getRect().getCenterX();
		float objectY = eObj.getRect().getCenterY();
		
		Line line = new Line(playerX, playerY, objectX,objectY);
		
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
		
		
		System.out.println("no collision");
		
		return false;
	}



}
