package actors;

import graphics.TurretGraphics;

import java.util.ArrayList;
import java.util.Properties;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import commands.CommandProvider;
import commands.GlobalInputListener;

public class Turret2 extends Actor {

//	private Image sprite;
	private TurretBehavior behavior;
	private TurretGraphics graphics;
	
//	private float x;
//	private float y;
	
	public Turret2(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		
	
		listener = new GlobalInputListener();
		status = new Status((float) x*map.getTileWidth(), (float) y*map.getTileHeight(), 20,20 );

		
		
		//Initialize graphics
		ArrayList<String> fileNames = new ArrayList<String>();

		// Image files to load;mount first!
		fileNames.add("data/mount.png");
		fileNames.add("data/turret.png");
		
		// define the relative location of the point of rotation from top left in pixels
		int centerRotateX = 20;
		int centerRotateY = 20;
		
		this.graphics = new TurretGraphics(status.getRect(),map,x,y,centerRotateX,centerRotateY,fileNames);
		
	}
	
	public void incorporateCollisionHandler(CollisionHandler collisionHandler) {
		
		status.setCollisionHandler(collisionHandler);
		
		behavior = new TurretBehavior(status, collisionHandler);
		
		engine = new TurretActionEngine(listener, status);
		
		
		listener.addProvider((CommandProvider)behavior);
		
	}
	
	
	//This shouldn't be necessary...? All render commands should be graphics dependent
	public void render(int mapX, int mapY, int mouseX, int mouseY){

		graphics.render(mapX, mapY,-25,-18,9);

	}

}
