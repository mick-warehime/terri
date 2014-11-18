package gameobjects;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import commands.DisplaceCommand;
import commands.MinimumDisplaceCommand;

public class MovingPlatform extends GameObject implements InteractiveCollideable{


	private float initialX;
	private float initialY;
	private float[] velocity = new float[2];
	private int switchTime;
	private int switchTimer;

	public MovingPlatform(int x, int y, int w, int h, String name,
			TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map, args);



		this.velocity[0] = Float.parseFloat((String)args.get("vx"));
		this.velocity[1] = Float.parseFloat((String)args.get("vy"));

		this.switchTimer = 0;
		this.switchTime = Integer.parseInt((String)args.get("switchTime"));

		
		this.initialX = this.shape.getX();
		this.initialY = this.shape.getY();


		// TODO Auto-generated constructor stub
	}

	private boolean cantMove(){

		boolean answer = switchTimer>=switchTime;

		answer = answer || collisionHandler.isCollided(shape);
		return answer;
	}

	public void update() throws SlickException{
		super.update();

		switchTimer +=1;
		//Check for direction change
		if(cantMove()){
			velocity[0] = -velocity[0];
			velocity[1] = -velocity[1];
			switchTimer = 0;
		}

		//displace
		shape.setX(shape.getX()+velocity[0]);
		shape.setY(shape.getY()+velocity[1]);






	}

	@Override
	public void onCollisionDo(Class collidingObjectClass, Shape collidingObjectShape) {
		

	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(Class collidingObjectClass, Shape collidingObjectShape) {
		ArrayList<Command> list = new ArrayList<Command>();
		
		list.add(new MinimumDisplaceCommand(2*velocity[1],'y'));
		list.add(new DisplaceCommand(velocity[0],'x'));
		
		return list;
	}

}
