package etherable;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import commands.DieCommand;

public class DeadlyObject extends EtherObject implements InteractiveCollideable{				 
	
	public DeadlyObject(int x, int y, int w, int h, TiledMap map) throws SlickException {
		super(x, y, w, h, map);
	}
	
	@Override
	public void onCollisionDo(String collidingObjectClass) {
		// TODO Auto-generated method stub
	}

	@Override
	public Command onCollisionBroadcast(String collidingObjectClass) {
		// TODO Auto-generated method stub
		return new DieCommand();
	}

	
}
