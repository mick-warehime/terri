package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;

public class DieCommand extends BasicCommand implements GenericCommand {

	

	public DieCommand() {
		super("Die");
		
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		actionEngine.die();
		
	}

}
