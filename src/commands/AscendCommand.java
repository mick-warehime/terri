package commands;



import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.PlayerActionEngine;


public class AscendCommand extends BasicCommand implements GenericCommand{

	
	public AscendCommand() {
		super("Ascend");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine){
		((PlayerActionEngine) engine).attemptAscend();
	}

	
}
