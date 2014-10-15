package commands;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

import main.CollisionHandler;


//Listens to command inputs from both player and world
public class GlobalInputListener {

	
	private PlayerInputListener listener = new PlayerInputListener();	 //keyboard and mouse inputs
	private CollisionHandler collisionHandler; //inputs due to game collisions
	private ArrayList<Command> currentActionCommands = new ArrayList<Command>();

	public GlobalInputListener(CollisionHandler collisionHandler) {
		
		this.collisionHandler = collisionHandler;
		
		
	}
	
	public void update(){
		
		receiveExternalInputs();
		return;
	}
	
	public ArrayList<Command> getCurrentActionCommands(){
		
		return currentActionCommands;
	}
	
	//For external inputs such as elevator collisions
	//or being hit by enemies
	private void receiveExternalInputs(){

		currentActionCommands.clear();
		//Receive external (game object) commands
		currentActionCommands.addAll(collisionHandler.popCollisionCommands()) ;

		//Receive player input commands
		currentActionCommands.addAll(listener.getCommands()) ;



	}
	public PlayerInputListener getPlayerInputListener(){
		return listener;
	}
	
	

}
