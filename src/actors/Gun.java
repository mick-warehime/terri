package actors;

import main.CollisionHandler;
import etherable.EtherObject;




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
			
			activeObject = collisionHandler.isAtEtherObject(mouseX,mouseY);
			if(activeObject!=null){
				activeObject.setObjectToEther();
				busyTime += busyTimeIncrement;
				status = "holding object";
			}
			
			
			
		}else if (status == "holding object"){ //Fire gun to place an ethered thing
			if(activeObject!=null){
				
				if(collisionHandler.canPlaceEtherAt(activeObject.getRect())){
					activeObject.put(mouseX,mouseY);
					busyTime += busyTimeIncrement;
					status = "object placed";
				}				
			}
			
		}
			
		
		return;
	}
	
	
	public void restoreActiveObject(){
		if (activeObject!=null){
			activeObject.restore();
			activeObject = null;
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
