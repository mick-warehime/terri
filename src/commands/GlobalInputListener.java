package commands;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;


//Listens to command inputs from generic providers
public class GlobalInputListener {

	
	private ArrayList <CommandProvider> providers; 	
	private ArrayList<Command> currentActionCommands ;

	public GlobalInputListener() {
		providers = new ArrayList <CommandProvider>();
		this.currentActionCommands = new ArrayList<Command>();
		
		
		
	}
	
	public void addProvider( CommandProvider provider){
		providers.add(provider);
	}
	
	public void update(){
		
		receiveExternalInputs();
		return;
	}
	
	public ArrayList<Command> getCurrentActionCommands(){
		
		return currentActionCommands;
	}
	//////////////////////////
	
	//For external inputs such as elevator collisions
	//or being hit by enemies
	private void receiveExternalInputs(){

		currentActionCommands.clear();
		
		for (CommandProvider provider: providers){
			currentActionCommands.addAll(provider.getCommands());
		}

	}
	
	

}
