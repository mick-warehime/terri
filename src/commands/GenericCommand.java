package commands;

import actors.ActionEngine;
import actors.PlayerActionEngine;

public interface GenericCommand {

	
	public void execute(ActionEngine actionEngine);
}
