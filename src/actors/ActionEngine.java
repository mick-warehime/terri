package actors;

import java.util.ArrayList;
import java.util.Vector;

import org.newdawn.slick.command.Command;

import commands.GenericCommand;
import commands.GlobalInputListener;
import commands.JumpCommand;
import commands.MoveCommand;


//Takes in command inputs and implements corresponding actions
public class ActionEngine {

	private GlobalInputListener listener;
//	private PlayerInputListener listener;
	private PlayerStatus status;
	private Gun gun;
	
	

	private float gravity = 1;
	private float vx = 0;
	private float vy = 0;
	private float ups = 20;
	private float wallUps = 15;
	private float runAcc = 2;
	private float runDec = 1;
	private float maxSpeed = 5;
	private int jumpTimer= 0;
	private int jumpTimerIncrement = 20;


	public ActionEngine(GlobalInputListener listener, PlayerStatus status, Gun gun){
		this.listener = listener;
		this.status = status;
		this.gun = gun;
	}

	public void update() {
		//Receive all command inputs
		listener.update();
		
		doActions();
		movePhysics();
		updateTimers();
	}


	// Attempts a displacement, or a smaller
	// one if possible. returns success or failure
	public boolean attemptDisplacement(float dx, float dy){
		boolean notCollided = false;

		//Null displacement always succeeds
		if (dx == 0 && dy == 0){return true;}

		//x only displacement
		if (dy == 0){
			if (dx>0){
				for (int ddx = (int) dx; ddx >0 ; ddx--){//Try displacements until they work
					status.displace(ddx,0);
					notCollided = !status.isCollided();
					if (notCollided){break;};
					status.displace(-ddx,0); // Only keep the displacement if no collision occured
				} 	
				return notCollided;
			}
			if (dx<0){
				for (int ddx = (int) dx; ddx <0 ; ddx++){//Try displacements until they work
					status.displace(ddx,0);
					notCollided = !status.isCollided();
					if (notCollided){break;};
					status.displace(-ddx,0); // Only keep the displacement if no collision occured
				} 	
				return notCollided;
			}
		}

		//y only displacement
		if (dx == 0){
			if (dy>0){
				for (int ddy =(int) dy; ddy >0 ; ddy--){//Try displacements until they work
					status.displace(0,ddy);
					notCollided = !status.isCollided();
					if (notCollided){break;};
					status.displace(0,-ddy); // Only keep the displacement if no collision occured
				} 	
				return notCollided;
			}
			if (dy<0){
				for (int ddy =(int) dy; ddy <0 ; ddy++){//Try displacements until they work
					status.displace(0,ddy);
					notCollided = !status.isCollided();
					if (notCollided){break;};
					status.displace(0,-ddy); // Only keep the displacement if no collision occured
				} 	
				return notCollided;
			}
		}

		//If x and y displacements occur, a success occurs if there is any
		// displacement
		boolean xAttemptSuccess = attemptDisplacement(dx,0);
		boolean yAttemptSuccess = attemptDisplacement(0,dy);

		return (xAttemptSuccess || yAttemptSuccess);

	}




	public void attemptRunTo(int direction) {
		//Only accelerate if not in air
		//if (!status.isTouchingGround()){return;}

		if (direction>0 ){
			vx = Math.min(vx + runAcc, maxSpeed);
		}else if(direction<0){
			vx = Math.max(vx - runAcc, -maxSpeed);
		}

		return;

	}

	public void attemptShoot(int mouseX, int mouseY){

		if (gun.canShoot(mouseX,mouseY)){
			gun.shootEtherBeam(mouseX, mouseY);
		}
		return;
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


	



	private void decelerate(){
		//		float vx = status.getVx();
		if (vx>0){ vx = Math.max(vx-runDec,(float) 0);}
		if (vx<0){ vx = Math.min(vx+runDec,(float) 0);}
		//		status.setVx(vx);
	}

	private void attemptWallJump(){

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
			status.displace(-1,0);
			answer = status.isCollided();
			status.displace(1,0);
		}else{
			status.displace(1,0);
			answer = status.isCollided();
			status.displace(-1,0);
		}
		//Check that jumpTimer is 0;
		answer = answer && (jumpTimer == 0); 


		return answer;
	}

	private boolean canJump(){
		return (status.isTouchingGround() && (jumpTimer==0)) ;
	}

	private void updateTimers(){
		if (jumpTimer>0){
			jumpTimer -=1;
		}
		//        System.out.println(jumpTimer);
	}

	//Displace the player according to his velocity, gravity, etc.
	// while checking for collisions
	private void movePhysics(){        

		
		

		//Horizontal movement and collision checking
		attemptDisplacement(vx,0);
		
		//Set vertical velocity to 0 if touching ground and 
		//going down.
		if (status.isTouchingGround() && vy>2){this.vy = 0;}
		
		//Vertical displacement
		boolean success = attemptDisplacement(0,vy);
		//Apply gravity if not touching the ground or
		// if a positive displacement(down) was successful
		boolean stopCond = !success && (vy>0); 
		if (!(status.isTouchingGround())&& !stopCond ){//
			this.vy += gravity;
		}

		if (!success){this.vy = 0;} //Only set vy to 0 on a vertical collision
		 
		
		System.out.println("velocity: " + vy);


		assert !status.isCollided() : "Player is inside an object!";

	}

	private void doActions(){

		//Get all player commands
		ArrayList<Command> currentActionCommands = listener.getCurrentActionCommands();

		boolean triedMove = false;
		boolean triedJump = false;

		//Do the associated actions
		for (Command cmd : currentActionCommands){
			((GenericCommand)cmd).execute(this);
			if (cmd instanceof JumpCommand){
				triedJump = true;
			}
			if (cmd instanceof MoveCommand){
				triedMove = true;
			}
		}
		//Attempt a wall jump (conditioned on having tried a jump and move)
		if (triedJump && triedMove){
			attemptWallJump();
		}
		//Decelerate if no move command given
		//		System.out.println(status.isTouchingGround());
		if (!triedMove ){//&& (status.isTouchingGround()
			decelerate();
		}
		
		

	}
	





}
