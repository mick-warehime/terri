package io;

import org.newdawn.slick.command.BasicCommand;

import main.Player;


public class MoveCommand extends BasicCommand implements GenericCommand{
	
	private int direction;
	

	
	public MoveCommand(String name, int direction) {
		super(name);
		this.direction = direction;
		
//		this.y = y;
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void execute(Player player){
		player.setVxToDirection(direction);

	}
	

	
}
