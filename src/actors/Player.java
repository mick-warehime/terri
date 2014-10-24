package actors;


import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import commands.GlobalInputListener;
import commands.KeyboardInputListener;

public class Player extends Actor {

	private KeyboardInputListener keyboard;

	private Gun gun;

	public Player(int x, int y, CollisionHandler collisionHandler) throws SlickException {
		
		sprite = new Image("data/head.png");
		
		keyboard = new KeyboardInputListener();
		listener = new GlobalInputListener();
		listener.addProvider(collisionHandler);
		listener.addProvider(keyboard);
		
		status = new Status((float) x, (float) y, collisionHandler);
		gun = new Gun(collisionHandler);
		engine = new PlayerActionEngine(listener,status, gun);
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
