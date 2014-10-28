package commands;


import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;

//Takes in input commands and implements them for the player
public class KeyboardInputListener implements InputProviderListener,CommandProvider{

	
//	private Player player;
	private ArrayList<Command> currentActions = new ArrayList<Command>();
	
	
	public KeyboardInputListener() {
		// TODO Auto-generated constructor stub
//		this.player = player;
	}
	
	
	private void addCommand(Command cmd){
		currentActions.add(cmd);
	}
	private void removeCommand(Command cmd){
		currentActions.remove(cmd);
	}
	

	public ArrayList<Command> getCommands(){
		return currentActions;
	}

	@Override
	public void controlPressed(Command cmd) {
		
		addCommand(cmd);
//		((MoveCommand)cmd).execute(player);
//		String message = "Pressed: "+cmd;
//		System.out.println(message);
		
	}

	@Override
	public void controlReleased(Command cmd) {
		// TODO Auto-generated method stub
		
		removeCommand(cmd);
		
//		String message = "Released: "+cmd;
//		System.out.println(message);
	}

}
