package etherable;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import commands.DieCommand;
import commands.TeleportCommand;

public class Teleporter extends EtherObject implements InteractiveCollideable{
	private int destX;
	private int destY;
		 
	public Teleporter(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map, args);
		// TODO Auto-generated constructor stub
		this.destX = Integer.parseInt((String) args.get("x"))*tileSize;
		this.destY = Integer.parseInt((String) args.get("y"))*tileSize;

	}

	@Override
	public void onCollisionDo(String collidingObjectClass) {
		// TODO Auto-generated method stub
	}

	@Override
	public Command onCollisionBroadcast(String collidingObjectClass) {
		// TODO Auto-generated method stub
		return new TeleportCommand(destX, destY);
	}

	protected void setRect(){
//		make the rect smaller so you dont collide with it immediately after transport
		int shrink = 2;
		rect = new Rectangle(x+shrink,y+shrink,w-2*shrink,h-2*shrink);
	}
}
