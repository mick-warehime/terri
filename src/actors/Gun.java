package actors;

import main.CollisionHandler;
import gameobjects.Etherable;
import gameobjects.Timed;




public class Gun {
	
	
	private String status = "idle";
	private int busyTime = 0;
	private int busyTimeIncrement = 30;
	private Etherable activeObject = null;
	private CollisionHandler collisionHandler;
	private int[] mousePos;
	
	
	
	public Gun(CollisionHandler collisionHandler, int[] mousePos) {
		this.collisionHandler = collisionHandler;
		this.mousePos = mousePos;
	}
	
	
	public void shootEtherBeam(){
		if (status == "idle"){ //Fire gun to turn things ether, if something is there
			
			Etherable etherableObject = checkForEtherableObject();
						
			if(canEtherize(etherableObject)){
				activeObject = etherableObject;
				activeObject.setObjectToEther();
				busyTime += busyTimeIncrement;
				status = "holding object";
			}							
		}else if (status == "holding object"){ //Fire gun to place an ethered thing
			
			if (canPut()){

				activeObject.put();
				busyTime += busyTimeIncrement;
				status = "object placed";
				if(activeObject instanceof Timed){
					activeObject = null;
					status = "idle";
				}
							
			}
			
		}
			
		return;
	}
	
	public Etherable checkForEtherableObject(){
		Etherable etherableObject = collisionHandler.isAtEtherObject(mousePos[0],mousePos[1]);
		if(etherableObject==null){
			etherableObject = collisionHandler.isAtEtherEnemy(mousePos[0],mousePos[1]);
			
		}
		return etherableObject;
	}
	
	public void restoreActiveObject(){
		
		if (canRestore()){

			
			activeObject.restore();
			activeObject = null;
			
			
			status = "idle";
		}
		
		
		return;
	}
	
	//Restores held/placed object
	
	
	//Determines whether the gun can trigger
	public boolean canShoot(){
		
		
		return busyTime==0;
	}


	public void update() {
		if (busyTime>0){
			busyTime-=1;
		}
		
		return;
		
	}
	
	
	private boolean canEtherize(Etherable testObject){
		if (testObject==null){
			return false;
		}
		boolean checkTransparent = true;
		return !collisionHandler.lineOfSightCollisionToPlayer(testObject.getShape(),checkTransparent);
	}
	
	//Tests whether a held object can be put in a given place.
	private boolean canPut(){
		if (activeObject==null){return false;}
			
		return activeObject.canPut();
	}
	
	private boolean canRestore(){
		
		if (activeObject==null){ return false;}
		
		
		return !collisionHandler.isCollidedWithPlayer(activeObject.getEtherRect());
	}
	

}
