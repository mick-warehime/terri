package main;

import io.GenericCommand;
import io.JumpCommand;
import io.MoveCommand;
import io.PlayerInputListener;

import java.util.Vector;

import org.newdawn.slick.command.Command;


//Takes in command inputs and implements corresponding actions
public class ActionEngine {

	private Player player;
	private PlayerInputListener listener;
	private PlayerStatus status;


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


	public ActionEngine(PlayerInputListener listener, PlayerStatus status){
		this.player= player;
		this.listener = listener;
		this.status = status;
	}

	public void update() {
		// TODO Auto-generated method stub
		doActions();
		movePhysics();
		updateTimers();
	}

	



	public void attemptRunTo(int direction) {
		//Only accelerate if not in air
		if (!status.isTouchingGround()){return;}

		if (direction>0 ){
			vx = Math.min(vx + runAcc, maxSpeed);
		}else if(direction<0){
			vx = Math.max(vx - runAcc, -maxSpeed);
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

	public void attemptJump() {
		//Check that player is on solid ground
		if (canJump()){
			this.vy -=ups;
			jumpTimer += jumpTimerIncrement;
		}

		return;

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

		//Apply gravity if not touching the ground
		if (!(status.isTouchingGround())){
			this.vy += gravity;
		}


		//Horizontal movement and collision checking
		status.attemptDisplacement(vx,0);

		//Vertical displacement
		boolean success = status.attemptDisplacement(0,vy);
		if (!success){this.vy = 0;} //Only set vy to 0 on a vertical collision




		assert !status.isCollided() : "Player is inside an object!";

	}
	
	private void doActions(){

		Vector<Command> commands = listener.getCommands();

		boolean triedMove = false;
		boolean triedJump = false;

		for (Command cmd : commands){
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
		if (!triedMove && (status.isTouchingGround())){
			decelerate();
		}

	}


}
