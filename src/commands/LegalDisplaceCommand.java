package commands;

import actors.ActionEngine;

//Displaces an actor in a direction
public class LegalDisplaceCommand implements GenericCommand {

	
	private char direction;
	private int maxRange;

	public LegalDisplaceCommand(char direction, int maxRange) {
		this.direction = direction;
		this.maxRange = maxRange;
		
		assert (direction == 'x' || direction == 'y')&&(maxRange>0) : "Unacceptable LegalDisplaceCommand input";
		
	}

	@Override
	public void execute(ActionEngine engine) {
		if (direction == 'x'){
			//this does the largest possible displacement
			engine.attemptDisplacement(maxRange,0); 
			//so reverse since want to do the minimum displacement
			engine.attemptDisplacement(-maxRange,0); 
		}

	}

}
