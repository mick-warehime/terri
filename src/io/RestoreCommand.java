package io;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;

public class RestoreCommand extends BasicCommand implements GenericCommand {

	

	public RestoreCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine) {
		engine.restoreActive();

	}

}
