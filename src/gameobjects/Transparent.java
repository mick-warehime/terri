package gameobjects;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import commands.RestoreCommand;

public class Transparent extends GameObject implements InteractiveCollideable{

	public Transparent(int tileX, int tileY, int widthInTiles,
			int heightInTiles, String name, TiledMap map, Properties args)
			throws SlickException {
		super(tileX, tileY, widthInTiles, heightInTiles, name, map, args);
		// TODO Auto-generated constructor stub
		opacity = (float) 0.4;
	}
	

	public boolean canCollide(){
		return false;
	}

	
	@Override
	public void onCollisionDo(Class<?> collidingObjectClass,
			Shape collidingObjectShape) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<Command> onCollisionBroadcast(Class collidingObjectClass, Shape collidingObjectShape) {
		
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new RestoreCommand());
		return list;
		// TODO Auto-generated method stub
		
	}
	
}
