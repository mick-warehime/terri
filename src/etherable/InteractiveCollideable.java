package etherable;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;


//An object that does something when you collide with it
//Gives actors commands and runs an internal function on collision
public interface InteractiveCollideable {

	public void onCollisionDo();
	public Command onCollisionBroadcast();
	public Rectangle getRect();
}
