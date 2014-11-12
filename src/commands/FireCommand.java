package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.PlayerActionEngine;

public class FireCommand extends BasicCommand implements GenericCommand {

	

	public FireCommand() {
		super("Shoot gun");
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine) {
		
		((PlayerActionEngine) engine).attemptShoot();
	}
	
	
	
}
