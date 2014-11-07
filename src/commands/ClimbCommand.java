package commands;



import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.PlayerActionEngine;


public class ClimbCommand extends BasicCommand implements GenericCommand{

	private int direction;
	public ClimbCommand(int direction) {
		super("Climb in" + direction + " direction");
		this.direction = direction;
	}

	@Override
	public void execute(ActionEngine engine){
		((PlayerActionEngine) engine).attemptClimb(direction);
	}

	
}
