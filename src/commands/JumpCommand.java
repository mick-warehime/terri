package commands;



import org.newdawn.slick.command.BasicCommand;

import actors.PlayerActionEngine;


public class JumpCommand extends BasicCommand implements GenericCommand{

	
	public JumpCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(PlayerActionEngine engine){
		engine.attemptJump();
		
	}

	
}
