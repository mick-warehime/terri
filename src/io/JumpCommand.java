package io;



import org.newdawn.slick.command.BasicCommand;

import main.Player;

public class JumpCommand extends BasicCommand implements GenericCommand{

	
	public JumpCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player player){
		player.attemptJump();
	}

	
}
