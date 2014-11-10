package actors;

import java.util.ArrayList;
import java.util.Properties;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;

import commands.DieCommand;
import commands.GlobalInputListener;
import gameobjects.InteractiveCollideable;

public class Enemy extends Actor implements InteractiveCollideable{

	private Behavior behavior;
	private int x;
	private int y;
	
	public Enemy(int x, int y, Properties args ) throws SlickException {
		super();
		
		this.x = x;
		this.y = y; //These shouldn't be necessary. Fix later
		
		
		
		listener = new GlobalInputListener();
		sprite = new Image("data/lemming.png");
		
		
		
	}
	
	public void update(){
		behavior.determine();
		super.update();
		assert (status != null) : "Error! Collision Handler not incorporated!";
		
	}

	@Override
	public void onCollisionDo(String collidingObjectClass) {
		// TODO Auto-generated method stub
		if (collidingObjectClass.equals("Player")){
			status.gainEffect("Collided with player", 1);
		}
	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(String collidingObjectClass) {
		ArrayList<Command> list = new ArrayList<Command>();
		if (collidingObjectClass == "Player"){
			list.add( new DieCommand());
		}
//		else{
//			list.add( new NullCommand());
//		}
		return list;
	}
	
	public void incorporateCollisionHandler(CollisionHandler collisionHandler){
		
		status = new Status((float) x, (float) y, collisionHandler,25,41 );
		engine = new EnemyActionEngine(listener, status);
		
		
		behavior = new Behavior(status, collisionHandler);
		
		listener.addProvider(behavior);
	}
	

	
}
