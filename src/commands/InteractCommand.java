package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.PlayerActionEngine;

public class InteractCommand extends BasicCommand implements GenericCommand{

	public InteractCommand() {
		super("Interact Command");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine) {
		((PlayerActionEngine) engine).attemptInteract();
		
	}

	
}
