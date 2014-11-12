package actors;

import graphics.EnemyGraphics;
import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import commands.GlobalInputListener;

public class Actor {

	protected Image sprite;
	protected GlobalInputListener listener;
	protected ActionEngine engine;
	protected Status status;

	public Actor() throws SlickException {
		
		
	}
	

	public float getX() {return status.getX();}

	public float getY() {return status.getY();}

 
	
	public void render( int mapX, int mapY) {
		sprite.draw((int)this.status.getX()-mapX,(int)this.status.getY()-mapY);    
	}
	
	public void render( int mapX, int mapY, int mouseX, int mouseY) {
		throw new UnsupportedOperationException(); 
	}
	
	public void update() {
	
		//Note: The order of these calls is important!
		//Update status
		status.updateEffects();
		
		//Receive all command inputs (some depend on status)
		listener.update();
		
		//Do actions (depends on listener)
		engine.update();
		
		
	}

	
	public boolean canCollide(){
		return true;
	}

	public Rectangle getRect() {
		return status.getRect();
	}
	
 
	
	public boolean isDying(){
		return status.isDying();
	}

	public void incorporateCollisionHandler(CollisionHandler collisionHandler) {
		throw new UnsupportedOperationException("Not implemented!");
		
	}


	public void update(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 

	}

}