package actors;

import commands.GlobalInputListener;

public class EnemyActionEngine extends ActionEngine {

	
	
	public EnemyActionEngine(GlobalInputListener listener, Status status) {
		super(listener, status);
		this.runAcc = (float ) 0.5;
		this.maxSpeed = (float) 1.5;
	}

}
