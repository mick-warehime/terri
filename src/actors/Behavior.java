package actors;

import java.util.ArrayList;

import main.CollisionHandler;

import org.newdawn.slick.command.Command;

import commands.CommandProvider;

public class Behavior implements CommandProvider{

	protected StatusNew status;
	protected CollisionHandler collisionHandler;
	protected ArrayList<Command> commandStack;

	public Behavior(StatusNew status2, CollisionHandler collisionHandler) {
		this.status = status2;
		this.collisionHandler = collisionHandler;
		this.commandStack = new ArrayList<Command>();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Command> getCommands() {
		return (ArrayList<Command>) commandStack.clone();
	}

	public void determine() {
		throw new UnsupportedOperationException("Not Implemented");
			
	}

}