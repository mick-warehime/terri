package main;

import java.util.ArrayList;
import org.newdawn.slick.geom.Rectangle;


public class CollisionHandler {


	private ArrayList<Rectangle> blocks;
	private ArrayList<Ether> etherObjects;
	private Rectangle playerRect;

	public CollisionHandler(ArrayList<Rectangle> blockedList, ArrayList<Ether> etherObjects){
		this.blocks = blockedList;
		this.etherObjects = etherObjects;
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
		for(Ether eObj: etherObjects){
			if(eObj.isCollided(rect)){
				return true;
			}
		}
		return false;
	}

	public Ether isAtEtherObject(int x, int y){

		for(Ether eObj: etherObjects){
			if(eObj.contains(x,y)){
				return eObj;
			}
		}

		return null;
	}


	public boolean canPlaceEtherAt(Rectangle rect){

		//	check if collided with permanent solid blocks	
		for(Rectangle r: blocks ){
			if(rect.intersects(r)){
				return false;
			}	
		}
		// check if collided with solid etherable Objects
		for(Ether eObj: etherObjects){
			if(eObj.isCollided(rect)){
				return false;
			}
		}
		if(playerRect.intersects(rect)){
			return false;
		}
		return true;
	}

 


}
