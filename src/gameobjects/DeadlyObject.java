package gameobjects;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.tiled.TiledMap;

import commands.DieCommand;
import commands.MinimumDisplaceCommand;

public class DeadlyObject extends GameObject implements InteractiveCollideable{
		
		 
	
	public DeadlyObject(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map, args);
		// TODO Auto-generated constructor stub
	}

	public boolean canCollide(){
		return false;
	}
	@Override
	public void onCollisionDo(String collidingObjectClass) {
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(String collidingObjectClass) {
		
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new DieCommand());
		return list;
		// TODO Auto-generated method stub
		
	}

	
}
