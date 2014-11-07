package gameobjects;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.tiled.TiledMap;

import commands.GainEffectCommand;

public class Ladder extends GameObject implements InteractiveCollideable{
		
		 
	
	public Ladder(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
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
	public Command onCollisionBroadcast(String collidingObjectClass) {
		// TODO Auto-generated method stub
		return new GainEffectCommand("touching ladder", 1);
	}


}