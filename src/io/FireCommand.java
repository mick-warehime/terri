package io;

import main.Level;

import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;

public class FireCommand extends BasicCommand implements GenericCommand {

	private Level level;
	private Input input;

	public FireCommand(String name, Input input, Level level) {
		super(name);
		this.level  =level;
		this.input = input;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(ActionEngine engine) {
		int mouseX = input.getMouseX()+level.getMapX();
		int mouseY = input.getMouseY()+level.getMapY();
		engine.attemptShoot(mouseX, mouseY);

	}

}
