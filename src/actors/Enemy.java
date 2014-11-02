package actors;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;

import commands.DieCommand;
import commands.GlobalInputListener;
import commands.NullCommand;
import etherable.InteractiveCollideable;

public class Enemy extends Actor implements InteractiveCollideable{

	private Behavior behavior;
	
	public Enemy(int x, int y, CollisionHandler collisionHandler ) throws SlickException {
		super();
		
		
		
		status = new Status((float) x, (float) y, collisionHandler,25,41 );
		
		listener = new GlobalInputListener();
		
		engine = new EnemyActionEngine(listener, status);
		
		sprite = new Image("data/lemming.png");
		
		behavior = new Behavior(status, collisionHandler);
		
		listener.addProvider(behavior);
		
	}
	
	public void update(){
		behavior.determine();
		super.update();
		
	}

	@Override
	public void onCollisionDo(String collidingObjectClass) {
		// TODO Auto-generated method stub
		if (collidingObjectClass == "Player"){
			status.gainEffect("Collided with player", 1);
		}
	}

	@Override
	public Command onCollisionBroadcast(String collidingObjectClass) {
		// TODO Auto-generated method stub
		if (collidingObjectClass == "Player"){
			return new DieCommand();
		}
		else{
			return new NullCommand();
		}
	}
	
	

	
}
