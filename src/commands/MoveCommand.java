package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.PlayerActionEngine;


public class MoveCommand extends BasicCommand implements GenericCommand{
	
	private int direction;
	

	
	public MoveCommand(int direction) {
		super("Move Command");
		this.direction = direction;
		
//		this.y = y;
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void execute(PlayerActionEngine engine){
		engine.attemptRunTo(direction);

	}
	

	
}
