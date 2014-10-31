package actors;

import java.util.ArrayList;

import main.CollisionHandler;

import org.newdawn.slick.command.Command;

import commands.CommandProvider;
import commands.DieCommand;
import commands.MoveCommand;

//Gives commands to an actor based on world conditions
public class Behavior implements CommandProvider{

	private Status status;
	private CollisionHandler collisionHandler;
	private ArrayList<Command> commandStack;
	private int moveDirection = -1;
	
	public Behavior(Status status, CollisionHandler collisionHandler) {
		this.status = status;
		this.collisionHandler = collisionHandler;
		this.commandStack = new ArrayList<Command>();
	}

	@Override
	public ArrayList<Command> getCommands() {
		// TODO Auto-generated method stub
		return (ArrayList<Command>) commandStack.clone();
	}
	
	//Determines commands to give to the actor
	public void determine(){
		commandStack.clear();
		
		//Resolve collision with player
		if (collisionHandler.isCollidedWithPlayer(status.getRect())){
			resolvePlayerCollision();
		}
		
		decideMovement();
			
		
		
		
	}
	
	//Apply these reactions on Player Collision
	private void resolvePlayerCollision(){
//		commandStack.add(new DieCommand());
		collisionHandler.addToCommandStack(new DieCommand());
	}
	
	private void decideMovement(){
		
		if (status.hasEffect("X collision")){
			moveDirection = -moveDirection;
		}
		
		commandStack.add(new MoveCommand(moveDirection));
		
		return;
	}

}
