package actors;

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
	private float x;
	private float y;
	
	public Turret2(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		
		this.x = x*map.getTileWidth();
		this.y = y*map.getTileHeight(); //These shouldn't be necessary. Fix later

		
		listener = new GlobalInputListener();
		sprite = new Image("data/turret.png");
		status = new Status((float) x, (float) y, sprite.getWidth(),sprite.getHeight() );

		
	}
	
	public void incorporateCollisionHandler(CollisionHandler collisionHandler) {
		
		status.setCollisionHandler(collisionHandler);
		
		behavior = new TurretBehavior(status, collisionHandler);
		
		engine = new TurretActionEngine(listener, status);
		
		
		listener.addProvider((CommandProvider)behavior);
		
	}

}
