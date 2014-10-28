package actors;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import commands.GlobalInputListener;

public class Enemy extends Actor {

	private Behavior behavior;
	
	public Enemy(int x, int y, CollisionHandler collisionHandler ) throws SlickException {
		super();
		
		
		
		status = new Status((float) x, (float) y, collisionHandler,25,41 );
		
		listener = new GlobalInputListener();
		
		engine = new ActionEngine(listener, status);
		
		sprite = new Image("data/lemming.png");
		
		behavior = new Behavior(status, collisionHandler);
		listener.addProvider(behavior);
		
	}
	
	public void update(){
		behavior.determine();
		super.update();
		
	}
	
	public boolean isDying(){
		return status.isDying();
	}
	
	

	
}
