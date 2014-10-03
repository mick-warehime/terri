package main;


import io.JumpCommand;
import io.MoveCommand;
import io.PlayerInputListener;

import java.util.Vector;

import io.GenericCommand;

import org.newdawn.slick.*;
//import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;

//TODO: Fix fall stutter. Add wall jumping.
public class Player {



	private Image sprite = new Image("data/head.png"); 
	//This object is what listens for commands for the player
	PlayerInputListener listener = new PlayerInputListener();	
	//Interprets commands and implements actions, physics
	private ActionEngine engine;
	//Stores information about player
	private PlayerStatus status;

	public Player(int x, int y, Collide collisionHandler) throws SlickException {

		      
		status = new PlayerStatus((float) x, (float) y, collisionHandler);
		engine = new ActionEngine(listener,status);
	}

	public int getX (){return status.getX();}
	public int getY (){return status.getY();}

	public void render(Graphics g, int mapX, int mapY){
		sprite.draw((int)this.status.getX()-mapX,(int)this.status.getY()-mapY);    
	}


	public void update(){

		engine.update();
		
	}

	public PlayerInputListener getListener(){
		return listener;
	}
	
	public PlayerStatus getStatus() {

		// TODO Auto-generated method stub
		return status;
	}

	
}
