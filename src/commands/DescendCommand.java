package commands;



import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.PlayerActionEngine;


public class DescendCommand extends BasicCommand implements GenericCommand{

	
	public DescendCommand() {
		super("Descend");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine){
		((PlayerActionEngine) engine).attemptDescend();
		
	}

	
}
