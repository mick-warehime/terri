package commands;

import org.newdawn.slick.command.BasicCommand;

import actors.ActionEngine;

//Displaces an actor in a direction, by the minimum required amount to 
//undo a collision
public class MinimumDisplaceCommand extends BasicCommand implements GenericCommand {

	
	private char direction;
	private float disp;

	public MinimumDisplaceCommand(float disp, char direction) {
		super("Legal displace in " + direction + " direction"); //Gives command name
		this.direction = direction;
		this.disp = disp;
		
		
		
	}

	@Override
	public void execute(ActionEngine engine) {
		
		engine.attemptDisplacement(disp, direction);
		engine.attemptDisplacement(-disp, direction);
		
//		System.out.println("displacing...");
//		//Do the displacement depending on assigned direction
//		if (direction == "+x"){
//			//this does the largest possible displacement
//			engine.attemptDisplacement(maxRange,'x'); 
//			//so reverse since want to do the minimum displacement
//			engine.attemptDisplacement(-maxRange,'x'); 
//		}
//		if (direction == "-x"){
//			//this does the largest possible displacement
//			engine.attemptDisplacement(-maxRange,'x'); 
//			//so reverse since want to do the minimum displacement
//			engine.attemptDisplacement(maxRange,'x'); 
//		}
//		if (direction == "+y"){
//			//this does the largest possible displacement
//			engine.attemptDisplacement(maxRange,'y'); 
//			//so reverse since want to do the minimum displacement
//			engine.attemptDisplacement(-maxRange, 'y'); 
//			
//		}
//		if (direction == "-y"){
//			//this does the largest possible displacement
//			engine.attemptDisplacement(-maxRange, 'y'); 
//			//so reverse since want to do the minimum displacement
//			engine.attemptDisplacement(maxRange,'y'); 
//			
//		}

	}

}
