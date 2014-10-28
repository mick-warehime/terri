package commands;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;

public interface CommandProvider {

	public ArrayList<Command> getCommands();
}
