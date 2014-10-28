package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.PlayerActionEngine;

public class RestoreCommand extends BasicCommand implements GenericCommand {

	

	public RestoreCommand() {
		super("restore held object");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine) {
		((PlayerActionEngine) engine).restoreActive();

	}

}
