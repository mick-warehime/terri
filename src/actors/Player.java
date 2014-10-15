package actors;


import main.CollisionHandler;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;

import commands.GlobalInputListener;
import commands.PlayerInputListener;

public class Player {



	private Image sprite = new Image("data/head.png"); 
	//This object is what listens for commands for the player (from all inputs)
	private GlobalInputListener listener;
	//Interprets commands and implements actions, physics
	private ActionEngine engine;
	//Stores information about player
	private PlayerStatus status;
	private Gun gun;

	public Player(int x, int y, CollisionHandler collisionHandler) throws SlickException {
		
		listener = new GlobalInputListener(collisionHandler);
		
		status = new PlayerStatus((float) x, (float) y, collisionHandler);
		gun = new Gun(collisionHandler);
		engine = new ActionEngine(listener,status, gun);
	}

	public int getX (){return status.getX();}
	public int getY (){return status.getY();}

	public void render(Graphics g, int mapX, int mapY){
		sprite.draw((int)this.status.getX()-mapX,(int)this.status.getY()-mapY);    
	}


	public void update(){

		engine.update();
		gun.update();
		
	}

	public PlayerInputListener getListener(){
		return listener.getPlayerInputListener();
	}
	
	public PlayerStatus getStatus() {

		// TODO Auto-generated method stub
		return status;
	}
	
	public Rectangle getRect(){
		return status.getRect();
	}

	public Gun getGun() {
		return gun;
	}

	
}
