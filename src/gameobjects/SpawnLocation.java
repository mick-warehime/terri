package gameobjects;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import actors.Actor;
import actors.Enemy;
import commands.DieCommand;

public class SpawnLocation extends GameObject implements ObjectCreator{
		
		 private int spawnX;
		 private int spawnY;
		 private Enemy spawnedEnemy;
		 private boolean produceEnemy;
		 private long waitTime;
		 private long waitTimer;
		 
	public SpawnLocation(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map, args);
		// TODO Auto-generated constructor stub
		
		// this are given in pixels
		this.spawnX = Integer.parseInt((String) args.get("sx"))*map.getTileWidth();
		this.spawnY = Integer.parseInt((String) args.get("sy"))*map.getTileHeight();

		
		produceEnemy = true;
		spawnedEnemy = new Enemy(spawnX,spawnY);
	}

	public void update() throws SlickException{
		
		if (spawnedEnemy.isDying()){
			produceEnemy = true;
			spawnedEnemy = new Enemy(spawnX,spawnY);			
		}
		
	}
	
	public boolean canCollide(){
		return true;
	}


	@Override
	public boolean hasObject() {
		// TODO Auto-generated method stub
		return produceEnemy;
	}

	@Override
	public Object getObject() throws SlickException {
		// TODO Auto-generated method stub
		produceEnemy = false;
		return spawnedEnemy;
	}

	
}
