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
	protected ActionEngine engine;
	protected Status status;

	public Actor() throws SlickException {
		
		
		
	}

	public int getX() {return status.getX();}

	public int getY() {return status.getY();}

	public void render(Graphics g, int mapX, int mapY) {
		sprite.draw((int)this.status.getX()-mapX,(int)this.status.getY()-mapY);    
	}

	public void update() {
	
		//Receive all command inputs
		listener.update();
		
		//Do actions
		engine.update();
//		if (status.isDying()){
//			System.out.println("Oh no!!!");
//		}
		
		
	}

	

//	public Status getStatus() {
//	
//		// TODO Auto-generated method stub
//		return status;
//	}

	public Rectangle getRect() {
		return status.getRect();
	}

}