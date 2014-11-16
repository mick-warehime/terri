package gameobjects;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import commands.TeleportCommand;

public class Teleporter extends GameObject implements InteractiveCollideable{
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
	public ArrayList<Command> onCollisionBroadcast(String collidingObjectClass) {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new TeleportCommand(destX, destY));
		return list;
		
	}

	protected void setRect(){
		//	make the shape smaller so you dont collide with it immediately after transport
		int shrink = 2;
		shape = new Rectangle(shape.getX()+shrink,shape.getY()+shrink,shape.getWidth()-2*shrink,shape.getHeight()-2*shrink);		
	}
	
	
	
}
