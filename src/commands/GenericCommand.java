package commands;

import actors.PlayerActionEngine;

public interface GenericCommand {

	
	public void execute(PlayerActionEngine engine);
}
