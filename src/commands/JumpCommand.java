package commands;



import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;


public class JumpCommand extends BasicCommand implements GenericCommand{

	
	public JumpCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine){
		engine.attemptJump();
		
	}

	
}
