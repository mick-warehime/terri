package actors;


import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import commands.GlobalInputListener;
import commands.KeyboardInputListener;

public class Player extends Actor {

	private KeyboardInputListener keyboard;
	private Gun gun;
	

	public Player(TiledMap map, CollisionHandler collisionHandler) throws SlickException {

		sprite = new Image("data/head.png");		
		
		keyboard = new KeyboardInputListener();
		listener = new GlobalInputListener();
		listener.addProvider(collisionHandler);
		listener.addProvider(keyboard);
		
		// GETS starting position from map
		// shifts initial y position by 1 pixel upwards otherwise dude gets stuck
		setStatus(map, collisionHandler);

		collisionHandler.addPlayerRect(status.getRect());

		gun = new Gun(collisionHandler);
		engine = new PlayerActionEngine(listener,status, gun);
	}

	private void setStatus(TiledMap map, CollisionHandler collisionHandler){
		
		//loop over all objects, find the start position object
		int px = 0;
		int py = 0;
		
		int objectGroupCount = map.getObjectGroupCount();
		for( int gi=0; gi < objectGroupCount; gi++ ) // gi = object group index
		{
			int objectCount = map.getObjectCount(gi);
			for( int oi=0; oi < objectCount; oi++ ) // oi = object index
			{
				String type = map.getObjectType(gi, oi);
				
				if(type.equals("start")){
					px = map.getObjectX(gi, oi);
					py = map.getObjectY(gi, oi);					
				}
			}
		}
		
		if(px == 0 && py == 0){
			System.out.println("STARTING POSITION OBJECT NOT FOUND!");
		}
		
		float h = sprite.getHeight();
		float w = sprite.getWidth();
		status = new Status((float) px, (float) py-1, collisionHandler, w, h);

	}

	public Gun getGun() {
		return gun;
	}

	public void update(){
		super.update();
		gun.update();
	}

	public KeyboardInputListener getListener() {
		return keyboard;
	}


}
