package main;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;

import etherable.Elevator;
import etherable.EtherObject;


public class CollisionHandler {


	private ArrayList<Rectangle> blocks;
	private ArrayList<EtherObject> etherObjects;
	//	private ArrayList<EtherObject> etherObjects2;
	private Rectangle playerRect;
	//Commands sent to the player if a collision
	// occurs
	private ArrayList<Command> collisionCommandStack = new ArrayList<Command>();
	

	public CollisionHandler(ArrayList<Rectangle> blockedList, ArrayList<EtherObject> etherObjects){
		this.blocks = blockedList;
		this.etherObjects = etherObjects;

		// add the collisionHandler to the ether objects that need it

		for(EtherObject eObj: etherObjects){
			if(eObj instanceof Elevator){
				eObj.setCollisionHandler(this);
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
		for(EtherObject eObj: etherObjects){
			if(eObj.isPut() || !eObj.isActive()){
				if(rect.intersects(eObj.getRect())){
					return true;
				}
			}
		}

		return false;
	}
	
	
	


	public EtherObject isAtEtherObject(int x, int y){

		for(EtherObject eObj: etherObjects){
			if(eObj.getRect().contains(x,y)){
				return eObj;
			}
		}

		return null;
	}


	public boolean canPlaceEtherAt(EtherObject etherObject){

		//	check if collided with permanent solid blocks	
		for(Rectangle r: blocks ){
			if(r.intersects(etherObject.getRect())){
				return false;
			}	
		}
		// check if collided with solid etherable Objects
		for(EtherObject eObj: etherObjects){
			// don't check with its own rect and dont check with objects that are currently being held
			if(eObj != etherObject){
				if(eObj.isPut() || !eObj.isActive()){
					if(etherObject.getRect().intersects(eObj.getRect())){
						return false;
					}
				}
			}
		}

		if(playerRect.intersects(etherObject.getRect())){
			return false;
		}
		return true;
	}

	public boolean isCollidedWithPlayer(EtherObject eObj){

		return playerRect.intersects(eObj.getRect());
	}
	
	public void addToCommandStack(Command cmd){
		collisionCommandStack.add(cmd);
		
	}
	public ArrayList<Command> popCollisionCommands(){
		ArrayList<Command> answer = (ArrayList<Command>) collisionCommandStack.clone();
		collisionCommandStack.clear();
		return answer;
	}



}
