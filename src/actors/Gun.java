package actors;

import main.CollisionHandler;
import main.Ether;



public class Gun {
	
	
	private String status = "idle";
	private int busyTime = 0;
	private int busyTimeIncrement = 30;
	private Ether heldObject = null;
	private CollisionHandler collisionHandler;
	
	public Gun(CollisionHandler collisionHandler) {
		this.collisionHandler = collisionHandler;
		
	}
	
	
	public void shootEtherBeam(int mouseX, int mouseY){
		if (status == "idle"){ //Fire gun to turn things ether, if something is there
			
			heldObject = collisionHandler.isAtEtherObject(mouseX,mouseY);
			if(heldObject!=null){
				heldObject.setObjectToEther();
				busyTime += busyTimeIncrement;
				status = "holding object";
			}
			
			
			
		}else if (status == "holding object"){ //Fire gun to place an ethered thing
			if(heldObject!=null){
				if(collisionHandler.canPlaceEtherAt(heldObject.getRect())){
					heldObject.put(mouseX,mouseY);
					busyTime += busyTimeIncrement;
					status = "object placed";
				}				
			}
			
		}
			
		
		return;
	}
	
	
	public void restoreHeldObject(){
		if (heldObject!=null){
			heldObject.restore();
			heldObject = null;
		}
		
		status = "idle";
		return;
	}
	
	//Restores held/placed object
	
	
	//Determines whether a shot is legal (will not be blocked)
	public boolean canShoot(int x, int y){
		return busyTime==0;
	}


	public void update() {
		if (busyTime>0){
			busyTime-=1;
		}
		
		return;
		
	}
	

}
