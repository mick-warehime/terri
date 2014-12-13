package actors;

import org.newdawn.slick.Graphics;

import main.CollisionHandler;
import gameobjects.Etherable;
import gameobjects.Rotateable;
import gameobjects.Timed;




public class Gun {
	
	
	static int IDLE_STATE = 0;
	static int HOLDING_STATE = 1;
	static int PUT_STATE = 2;
	
	private int state = IDLE_STATE;
	private int busyTime = 0;
	private static int busyTimeIncrement = 30;
	private Etherable activeObject = null;
	private CollisionHandler collisionHandler;
	private int[] mousePos;
	
	
	
	public Gun(CollisionHandler collisionHandler, int[] mousePos) {
		this.collisionHandler = collisionHandler;
		this.mousePos = mousePos;
	}
	
	
	
	public void attemptRotate( boolean rotateClockwise){
		
		if (canRotate()){
			((Rotateable)activeObject).rotate(rotateClockwise, mousePos);
			busyTime += busyTimeIncrement;
		}
		
		
		
	}
	
	private boolean canRotate() {
		boolean answer = (state == HOLDING_STATE);
		answer = answer && (activeObject instanceof Rotateable);
		answer = answer && (busyTime == 0);
				
		// TODO Auto-generated method stub
		return answer;
	}



	public void shootEtherBeam(){
		if (state == IDLE_STATE){ //Fire gun to turn things ether, if something is there
			
			Etherable etherableObject = checkForEtherableObject();
						
			if(canEtherize(etherableObject)){
				activeObject = etherableObject;
				activeObject.setObjectToEther();
				busyTime += busyTimeIncrement;
				state = HOLDING_STATE;
			}							
		}else if (state == HOLDING_STATE){ //Fire gun to place an ethered thing
			
			if (canPut()){

				activeObject.put();
				busyTime += busyTimeIncrement;
				state = PUT_STATE;
				if(activeObject instanceof Timed){
					activeObject = null;
					state = IDLE_STATE;
				}
							
			}
			
		}
			
		return;
	}
	
	public Etherable checkForEtherableObject(){
		Etherable etherableObject = collisionHandler.etherObjectAtPosition(mousePos[0],mousePos[1]);
		if(etherableObject==null){
			etherableObject = collisionHandler.isAtEtherEnemy(mousePos[0],mousePos[1]);
			
		}
		return etherableObject;
	}
	
	public void restoreActiveObject(){
		
		if (canRestore()){

			
			activeObject.restore();
			activeObject = null;
			
			
			state = IDLE_STATE;
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
		boolean answer = activeObject.canRestore();
		answer = answer && !collisionHandler.isCollidedWithPlayer(activeObject.getEtherRect());
		
		return answer;
	}


//	public void render(Graphics g, int initX, int initY, int mapX, int mapY ) {
//		
//		g.drawLine(initX, initY, mousePos[0]+mapX, mousePos[1]+mapY);
//		System.out.println(mousePos[0] + "," + mousePos[1]);
//	}
	

}
