package actors;

import org.newdawn.slick.Graphics;
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

	public void render(Graphics g, int mapX, int mapY) {
		sprite.draw((int)this.status.getX()-mapX,(int)this.status.getY()-mapY);    
	}

	public void update() {
	
		//Note: The order of these calls is important!
		//Update status
		status.update();
		
		//Receive all command inputs (some depend on status)
		listener.update();
		
		//Do actions (depends on listener)
		engine.update();
		
		
	}

	

//	public Status getStatus() {
//	
//		// TODO Auto-generated method stub
//		return status;
//	}

	public Rectangle getRect() {
		return status.getRect();
	}

	public boolean isDying(){
		return status.isDying();
	}

}