package actors;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import commands.GlobalInputListener;
import commands.KeyboardInputListener;

public class Actor {

	protected Image sprite;
	protected GlobalInputListener listener;
	protected PlayerActionEngine engine;
	protected Status status;

	public Actor() throws SlickException {
		super();
		
		
	}

	public int getX() {return status.getX();}

	public int getY() {return status.getY();}

	public void render(Graphics g, int mapX, int mapY) {
		sprite.draw((int)this.status.getX()-mapX,(int)this.status.getY()-mapY);    
	}

	public void update() {
	
		engine.update();
		
		
	}

	

	public Status getStatus() {
	
		// TODO Auto-generated method stub
		return status;
	}

	public Rectangle getRect() {
		return status.getRect();
	}

}