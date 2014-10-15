package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;

//Displaces an actor in a direction
public class LegalDisplaceCommand extends BasicCommand implements GenericCommand {

	
	private String direction;
	private int maxRange;

	public LegalDisplaceCommand(String direction, int maxRange) {
		super("Legal displace in " + direction + " direction"); //Gives command name
		this.direction = direction;
		this.maxRange = maxRange;
		
		assert (direction == "-x" || direction == "+x" || direction == "-y" ||direction == "+y" )
		&&(maxRange>0) : "Unacceptable LegalDisplaceCommand input";
		
	}

	@Override
	public void execute(ActionEngine engine) {
		//Do the displacement depending on assigned direction
		if (direction == "+x"){
			//this does the largest possible displacement
			engine.attemptDisplacement(maxRange,0); 
			//so reverse since want to do the minimum displacement
			engine.attemptDisplacement(-maxRange,0); 
		}
		if (direction == "-x"){
			//this does the largest possible displacement
			engine.attemptDisplacement(-maxRange,0); 
			//so reverse since want to do the minimum displacement
			engine.attemptDisplacement(maxRange,0); 
		}
		if (direction == "+y"){
			//this does the largest possible displacement
			engine.attemptDisplacement(0,maxRange); 
			//so reverse since want to do the minimum displacement
			engine.attemptDisplacement(0,-maxRange); 
		}
		if (direction == "-y"){
			//this does the largest possible displacement
			engine.attemptDisplacement(0,-maxRange); 
			//so reverse since want to do the minimum displacement
			engine.attemptDisplacement(0,maxRange); 
		}

	}

}
