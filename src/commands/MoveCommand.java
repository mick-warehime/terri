package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;


public class MoveCommand extends BasicCommand implements GenericCommand{
	
	private int direction;
	

	
	public MoveCommand(int direction) {
		super("Move " + direction );
		this.direction = direction;

	}
	

	@Override
	public void execute(ActionEngine engine){
		engine.attemptRunTo(direction);

	}
	

	
}
