package actors;

import java.util.ArrayList;

import main.CollisionHandler;

import org.newdawn.slick.command.Command;

import commands.CommandProvider;
import commands.MoveCommand;

//Gives commands to an actor based on world conditions
public class LemmingBehavior extends Behavior implements CommandProvider{

	private int moveDirection = -1;
	
	
	public LemmingBehavior(Status status, CollisionHandler collisionHandler) {
		super(status, collisionHandler);

	}

	public void determine(){
		commandStack.clear();


		if (status.hasEffect("Collided with player")){
			resolvePlayerCollision();
		}

		//Resolve collision with interactives
		resolveInteractiveCollisions();

		decideMovement();
	}
	
	
	//Apply these reactions on Player Collision
	private void resolvePlayerCollision(){
//		commandStack.add(new DieCommand());

	}
	
	private void decideMovement(){
		
		if (status.hasEffect("x collision")){
			moveDirection = -moveDirection;
		}
		
		commandStack.add(new MoveCommand(moveDirection));
		
		return;
	}
	
	private void resolveInteractiveCollisions(){
		ArrayList<Command> newCommands = collisionHandler.resolveInteractiveCollisions(status.getRect(), "Enemy");
		commandStack.addAll(newCommands);
		return;
	}

}
