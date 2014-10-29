package actors;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import commands.GenericCommand;
import commands.GlobalInputListener;
import commands.JumpCommand;
import commands.MoveCommand;

public class ActionEngine {

	
	 
	protected GlobalInputListener listener;
	protected Status status;
	private float gravity = 1;
	protected float vx = 0;
	protected float vy = 0;
	private float runAcc = 2;
	private float runDec = 1;

	private float maxSpeed = 5;
	
	


	public ActionEngine(GlobalInputListener listener, Status status) {
		
		this.listener = listener;
		this.status = status;
	}

	public void update() {
		

		doActions();
		movePhysics();
		updateTimers();
		
		
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
	
	public void die(){
		status.setDying(true);
		return;
	}
	
	//////////////////////////

	protected void decelerate() {
		//		float vx = status.getVx();
		if (vx>0){ vx = Math.max(vx-runDec,(float) 0);}
		if (vx<0){ vx = Math.min(vx+runDec,(float) 0);}
		//		status.setVx(vx);
	}

	private void movePhysics() {        

		
		//Horizontal movement and collision checking
		boolean success = attemptDisplacement(vx,0);
		if (!success){
			status.gainEffect("X collision", 1);
		}

		//Set vertical velocity to 0 if touching ground and 
		//going down.
		if (status.isTouchingGround() && vy>2){this.vy = 0;}

		//Vertical displacement
		success = attemptDisplacement(0,vy);
		//Apply gravity if not touching the ground or
		// if a positive displacement(down) was successful
		boolean stopCond = !success && (vy>0); 
		if (!(status.isTouchingGround())&& !stopCond ){//
			this.vy += gravity;
		}

		if (!success){this.vy = 0;} //Only set vy to 0 on a vertical collision


		//		System.out.println("velocity: " + vy);

		
		assert !status.isCollided() : "Actor is inside an object!";

	}

	protected void updateTimers(){
		return;
	}

	protected void doActions() {

		//Get all player commands
		ArrayList<Command> currentActionCommands = listener.getCurrentActionCommands();
		

		//Do the associated actions
		for (Command cmd : currentActionCommands){
			((GenericCommand)cmd).execute(this);
			
		}
		



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

}