package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;

public class TeleportCommand extends BasicCommand implements GenericCommand {

	private int destX;
	private int destY;
	
	public TeleportCommand(int destX, int destY) {
		super("Teleport");
		this.destX = destX;
		this.destY = destY;
	}

	@Override
	public void execute(ActionEngine actionEngine) {
		actionEngine.Teleport(destX,destY);
	}

}
