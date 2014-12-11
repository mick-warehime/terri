package actors;

import graphics.ActorGraphics;
import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import commands.GlobalInputListener;

public abstract class Actor {

	protected ActorGraphics graphics;
	protected GlobalInputListener listener;
	protected ActionEngine engine;
	protected Status status;

	public Actor() throws SlickException {
		
		
	}
	

	public float getX() {return status.getX();}

	public float getY() {return status.getY();}

 
	
	public  void render( int mapX, int mapY){};
	
	
	
	public void update() {
	
		//Note: The order of these calls is important!
		//Update status
		status.updateEffects();
		
		//Receive all command inputs (some depend on status)
		listener.receiveExternalInputs();
		
		//Do actions (depends on listener)
		engine.update();
		
		
	}

	
	public boolean canCollide(){
		return true;
	}

	public Rectangle getShape() {
		return status.getRect();
	}
	
 
	
	public boolean isDying(){
		return status.isDying();
	}

	public void incorporateCollisionHandler(CollisionHandler collisionHandler) {
		throw new UnsupportedOperationException("Not implemented!");
		
	}



}