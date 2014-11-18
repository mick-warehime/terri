package gameobjects;

import java.util.ArrayList;

import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Shape;

import actors.Actor;
import actors.Enemy;

public class TurretEffectiveRange implements InteractiveCollideable {

	Shape rangeDelimiter;
	private ArrayList<Shape> targets;

	public TurretEffectiveRange( Shape rangeDelimiter, ArrayList<Shape> targets){
		this.rangeDelimiter = rangeDelimiter;
		this.targets = targets;


	}

	@Override
	public void onCollisionDo(Class<?> collidingObjectClass,
			Shape collidingObjectShape) {
		
		
		boolean validTarget = false;
		Class<Actor> targetableClass = Actor.class;
		
		Class<?> testClass = collidingObjectClass;
		
		//You have to loop over all possible superclasses
		while (testClass != null){
			if(testClass.equals(targetableClass)){
				validTarget = true;
				break;
			}
			testClass = testClass.getSuperclass();
		}
		
		
		if (validTarget){
			if (!targets.contains(collidingObjectShape)){
				targets.add(collidingObjectShape);
			}
		}



	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(Class<?> collidingObjectClass,
			Shape collidingObjectShape) {
		// TODO Auto-generated method stub
		return new ArrayList<Command>();
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return rangeDelimiter;
	}


}
