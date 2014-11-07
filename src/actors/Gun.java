package actors;

import main.CollisionHandler;
import gameobjects.EtherObject;
import gameobjects.Timed;




public class Gun {
	
	
	private String status = "idle";
	private int busyTime = 0;
	private int busyTimeIncrement = 30;
	private EtherObject activeObject = null;
	private CollisionHandler collisionHandler;
	
	public Gun(CollisionHandler collisionHandler) {
		this.collisionHandler = collisionHandler;
		
	}
	
	
	public void shootEtherBeam(int mouseX, int mouseY){
		if (status == "idle"){ //Fire gun to turn things ether, if something is there
			
			EtherObject testObject = collisionHandler.isAtEtherObject(mouseX,mouseY);
			
			if(canEtherize(testObject)){
				activeObject = testObject;
				activeObject.setObjectToEther();
				busyTime += busyTimeIncrement;
				status = "holding object";
			}							
		}else if (status == "holding object"){ //Fire gun to place an ethered thing
			
			if (canPut()){

				activeObject.put(mouseX,mouseY);
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
	public boolean canShoot(int x, int y){
		
		
		
		return busyTime==0;
	}


	public void update() {
		if (busyTime>0){
			busyTime-=1;
		}
		
		return;
		
	}
	
	
	private boolean canEtherize(EtherObject testObject){
		if (testObject==null){
			return false;
		}
		
		return !collisionHandler.lineOfSightCollision(testObject);
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
