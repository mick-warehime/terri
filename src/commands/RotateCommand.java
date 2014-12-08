package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;
import actors.PlayerActionEngine;

public class RotateCommand extends BasicCommand implements GenericCommand {

	
	private boolean rotatesClockwise;

	public RotateCommand(boolean rotatesClockwise) {
		super("Rotate command");
		this.rotatesClockwise= rotatesClockwise;
		
		
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		((PlayerActionEngine)actionEngine).attemptRotate(rotatesClockwise);
		
	}

}
