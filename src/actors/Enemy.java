package actors;

import java.util.ArrayList;
import java.util.Properties;

import main.CollisionHandler;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import commands.DieCommand;
import commands.GlobalInputListener;
import gameobjects.InteractiveCollideable;
import graphics.EnemyGraphics;

public class Enemy extends Actor implements InteractiveCollideable{

	private LemmingBehavior behavior;
	private int x;
	private int y;
	private EnemyGraphics graphics;
	protected Rectangle rect;

	public Enemy(int x, int y, int w, int h, String name, TiledMap map, Properties args ) throws SlickException {
		super();

		this.x = x*map.getTileWidth();
		this.y = y*map.getTileHeight(); //These shouldn't be necessary. Fix later

		listener = new GlobalInputListener();
		
		rect = new Rectangle(this.x,this.y,32, 48);

		status = new Status(rect);

 		graphics = new EnemyGraphics(status,"data/enemy1.png");
 	}

	public Enemy(int xPixels, int yPixels) throws SlickException {
		super();
		
		rect = new Rectangle(xPixels,yPixels,32,48);
		 
		listener = new GlobalInputListener();
				
		status = new Status(rect);

 		graphics = new EnemyGraphics(status,"data/enemy1.png");
 	}
	
	public void incorporateCollisionHandler(CollisionHandler collisionHandler){

		// status = new Status((float) x, (float) y, collisionHandler,sprite.getWidth(),sprite.getHeight() );
		status.setCollisionHandler(collisionHandler);

		engine = new EnemyActionEngine(listener, status);

		behavior = new LemmingBehavior(status, collisionHandler);

		listener.addProvider(behavior);
		
	}

	public void update(){
		behavior.determine();
		super.update();
		assert (status != null) : "Error! Collision Handler not incorporated!";
	}

	@Override
	public void onCollisionDo(Class collidingObjectClass, Shape collidingObjectShape) {
		// TODO Auto-generated method stub
		if (collidingObjectClass.equals(Player.class)){
			status.gainEffect("Collided with player", 1);
		}
	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(Class collidingObjectClass, Shape collidingObjectShape) {
		ArrayList<Command> list = new ArrayList<Command>();
		if (collidingObjectClass.equals(Player.class)){
			list.add( new DieCommand());
		}
		return list;
	}
	
	public void render( int mapX, int mapY) {
		graphics.render(rect.getX() - mapX, rect.getY() - mapY); 
	}





}
