package commands;



import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.PlayerActionEngine;


public class JumpCommand extends BasicCommand implements GenericCommand{

	
	public JumpCommand() {
		super("Jump");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine){
		((PlayerActionEngine) engine).attemptJump();
		
	}

	
}
