package actors;


import main.Level;
import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import commands.GlobalInputListener;
import commands.KeyboardInputListener;

public class Player extends Actor {

	private KeyboardInputListener keyboard;
	private Gun gun;
	private Rectangle rect;
	

	public Player(int x, int y, CollisionHandler collisionHandler, int[] mousePos) throws SlickException {

 		
		sprite = new Image("data/head.png");		
		
		keyboard = new KeyboardInputListener();
		listener = new GlobalInputListener();
		listener.addProvider(collisionHandler);
		listener.addProvider(keyboard);
		
		
		// GETS starting position from map
		// shifts initial y position by 1 pixel upwards otherwise dude gets stuck
		setStatus( x,y, collisionHandler);

		collisionHandler.addPlayerRect(status.getRect());

		gun = new Gun(collisionHandler, mousePos);
		engine = new PlayerActionEngine(listener,status, gun);
	}

	public void render( int mapX, int mapY) {
		sprite.draw((int)this.status.getX()-mapX,(int)this.status.getY()-mapY);    
	}

	
	public void setStatus(int x, int y, CollisionHandler collisionHandler){
				
		//loop over all objects, find the start position object
		int px = x;
		int py = y;
				
		float h = sprite.getHeight();
		float w = sprite.getWidth();
		rect = new Rectangle((float) px, (float) py-1, w, h);
		status = new StatusNew(rect);
		status.setCollisionHandler(collisionHandler);
		
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
	
	public void resetPlayer(Level level){
		status.setAlive();
		status.setX(level.getProgressX());
		status.setY(level.getProgressY());
	}



}
