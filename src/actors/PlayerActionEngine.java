package actors;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import commands.GlobalInputListener;
import commands.JumpCommand;
import commands.MoveCommand;
import etherable.GameObject;


//Takes in command inputs and implements corresponding actions
public class PlayerActionEngine extends ActionEngine {

	private Gun gun;
	
//	private ArrayList <Action> actions;

	private float ups = 20;
	private float wallUps = 15;
	private int jumpTimer= 0;
	private int jumpTimerIncrement = 20;
	private int interactTimer = 0;
	private int interactTimerIncrement = 20;
	
	private float runDec = 1;
	

	


	public PlayerActionEngine(GlobalInputListener listener, Status status, Gun gun){
		super(listener,status);
		this.gun = gun;
		this.runAcc = 2;
		this.maxSpeed = 5;

	}

	public void attemptShoot(int mouseX, int mouseY){

		if (gun.canShoot(mouseX,mouseY)){
			gun.shootEtherBeam(mouseX, mouseY);
		}
		return;
	}

	public void attemptInteract(){
		//Get nearby objects to interact with
		ArrayList<GameObject> objects = status.nearbyInteractives();
		
		//Interact, if possible
		if (interactTimer==0 && !objects.isEmpty()){
			for (GameObject gObj: objects){
				gObj.toggle();
			}
			
			interactTimer+= interactTimerIncrement;
		}
	}
	
	public void restoreActive(){
		gun.restoreActiveObject();
	}

	public void attemptJump() {
		//Check that player is on solid ground
		if (canJump()){
			this.vy -=ups;
			jumpTimer += jumpTimerIncrement;
		}

		return;

	}

	////////////////


	



	public void attemptWallJump(){

		if (canWallJump()){

			vy -= wallUps;
			jumpTimer += jumpTimerIncrement;
			vx = - vx;
		}


		return;
	}


	private boolean canWallJump(){
		boolean answer = false;

		//Check that I am touching a wall in the direction
		//that I'm moving	
		if (vx<0){
			status.displace(-1,'X');
			answer = status.isCollided();
			status.displace(1,'X');
		}else{
			status.displace(1,'X');
			answer = status.isCollided();
			status.displace(-1,'X');
		}
		//Check that jumpTimer is 0;
		answer = answer && (jumpTimer == 0); 


		return answer;
	}

	private boolean canJump(){
		return (status.isTouchingGround() && (jumpTimer==0)) ;
	}

	protected void updateTimers(){
		if (jumpTimer>0){
			jumpTimer -=1;
		}
		if (interactTimer>0){
			interactTimer -=1;
		}
		
		
	}
	
	
	protected void doActions() {
		
		//Do actions from commands
		super.doActions();
		//Get all player commands
		ArrayList<Command> currentActionCommands = listener.getCurrentActionCommands();

//		System.out.println(currentActionCommands);
		
		boolean triedMove = false;
		boolean triedJump = false;

		//Check which actions are done (There is a better what to do this)
		for (Command cmd : currentActionCommands){
			if (cmd instanceof JumpCommand){
				triedJump = true;
			}
			if (cmd instanceof MoveCommand){
				triedMove = true;
			}
		}
//		//Attempt a wall jump (conditioned on having tried a jump and move)
//		if (triedJump && triedMove){
//			attemptWallJump();
//		}
		//Decelerate if no move command given
		//		System.out.println(status.isTouchingGround());
		if (!triedMove ){//&& (status.isTouchingGround()
			decelerate();
		}
		
		

	}

	//////////////////////////
	
	protected void decelerate() {
		
		if (vx>0){ vx = Math.max(vx-runDec,(float) 0);}
		if (vx<0){ vx = Math.min(vx+runDec,(float) 0);}
		
	}
	




}
