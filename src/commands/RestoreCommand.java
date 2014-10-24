package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.PlayerActionEngine;

public class RestoreCommand extends BasicCommand implements GenericCommand {

	

	public RestoreCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(PlayerActionEngine engine) {
		engine.restoreActive();

	}

}
