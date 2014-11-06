package actors;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import commands.GenericCommand;
import commands.GlobalInputListener;


public class ActionEngine {

	
	 
	protected GlobalInputListener listener;
	protected Status status;
	private float gravity = 1;
	protected float vx = 0;
	protected float vy = 0;
	protected float runAcc = 0; //Default values
	protected float maxSpeed = 0;
	
	


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

	private void movePhysics() {        

		
		//Horizontal movement and collision checking
		boolean success = attemptDisplacement(vx,'x');
		if (!success){
			status.gainEffect("X collision", 1);
			vx = 0;
		}

		//Set vertical velocity to 0 if touching ground and 
		//going down.
		if (status.isTouchingGround() && vy>2){this.vy = 0;}

		//Vertical displacement
		success = attemptDisplacement(vy,'y');
		//Apply gravity if not touching the ground or
		// if a positive displacement(down) was successful
		boolean stopCond = !success && (vy>0); 
		if (!(status.isTouchingGround())&& !stopCond ){//
			this.vy += gravity;
		}

		if (!success){this.vy = (float) 0;} //Only set vy to 0 on a vertical collision


		
		
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


	//Does the maximum possible displacement in a given direction
	//Does this through a bisection type algorithm
	//Returns true if any displacement was possible
	
	public boolean attemptDisplacement(float disp, char XorY){
		
		float dMax = disp;
		float lastValid = 0;
		
		if (disp == 0){
			return true;
		}
		
		int sign = 1;
		if (disp<0){ sign = -1;} //Accounts for a negative disp below
		
		float diff = (float) Math.abs(dMax*0.5);
		float minDiff = (float) 0.1;
		
		
		boolean collided;
		
		//Check displacements dMax until the maximum displacement
		// is reached. Store largest valid displacement.

		while (diff>minDiff){ //diff starts at dMax/2

			//Check if dMax displacement collides
			status.displace(dMax,XorY);
			collided = status.isCollided();
			status.displace(-dMax, XorY);

			if (!collided && dMax == disp){ //Maximum initial displacement doesn't collide, so accept it
				status.displace(dMax, XorY);
				return true;
			}

			if (collided){ //dMax collides, so decrease it.
				dMax -= sign*diff;

			}
			//Non-maximal displacement doesn't collide, so increase it
			// and store this valid displacement.
			if (!collided){ 
				lastValid = dMax;
				dMax += sign*diff;
			}

			diff = (float) (diff*.5);

		}
		
		//Do the last valid displacement 
		status.displace(lastValid,XorY);
		return (Math.abs(lastValid)>0); //Success if any displacement occurred


	}

	public void Teleport(int destX, int destY) {
		// TODO Auto-generated method stub
		status.setX(destX);
		status.setY(destY);
	}

}