package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;


public class DisplaceCommand extends BasicCommand implements GenericCommand{
	
	private char direction;
	private float displacement;

	
	public DisplaceCommand(float displacement, char direction) {
		super("Move " + direction );
		this.direction = direction;
		this.displacement = displacement;
	}
	

	@Override
	public void execute(ActionEngine engine){
		engine.attemptDisplacement(displacement,direction);
	}
	

	
}
