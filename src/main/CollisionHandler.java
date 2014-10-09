package main;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import etherable.EtherObject;


public class CollisionHandler {


	private ArrayList<Rectangle> blocks;
	private ArrayList<EtherObject> etherObjects;
//	private ArrayList<EtherObject> etherObjects2;
	private Rectangle playerRect;

	public CollisionHandler(ArrayList<Rectangle> blockedList, ArrayList<EtherObject> etherObjects){
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


	public boolean canPlaceEtherAt(Rectangle rect){

		//	check if collided with permanent solid blocks	
		for(Rectangle r: blocks ){
			if(rect.intersects(r)){
				return false;
			}	
		}
		// check if collided with solid etherable Objects
		for(EtherObject eObj: etherObjects){
			if(eObj.isPut() || !eObj.isActive()){
				if(rect.intersects(eObj.getRect())){
					return false;
				}
			}
		}
		if(playerRect.intersects(rect)){
			return false;
		}
		return true;
	}

 


}
