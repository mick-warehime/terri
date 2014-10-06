package main;

import org.newdawn.slick.geom.Rectangle;

public class PlayerStatus {

//	private Player player;
	private float x;
	private float y;

	private Rectangle rect;
	private Collide collisionHandler;

	public PlayerStatus(float x, float y, Collide collisionHandler) {
//		this.player = player;


		this.x = x;
		this.y = y;
		
		rect = new Rectangle(0,0,32,32);
		
		System.out.println("Crahs here???");
		
		rect.setX((int) x);
		rect.setY((int) y);
		
		System.out.println("Crahs here???");
		
		this.collisionHandler = collisionHandler;
		this.collisionHandler.addPlayerRect(rect);
		
		
	}

	public boolean isTouchingGround() {
		boolean answer = false;
		displace(0,+1);
		answer = isCollided();
		displace(0,-1);

		
		
		return answer;
	}


	public int getX (){return (int)x;}
	public int getY (){return (int)y;}
	
	//Fix this. Level is a global variable
	public boolean isCollided(){
		//            System.out.println(Level.isBlocked(rect));
		//        System.out.println(collisionHandler.isCollided(rect));
		
		boolean answer = collisionHandler.isCollided(rect);
//		if (answer){System.out.println("YES");}
//		else if (!answer){System.out.println("YES");};
		return answer;
	}

	//Displaces the player 
	public void displace(float dx, float dy){
		this.x += dx;
		this.y += dy;
		rect.setX((int)this.x);
		rect.setY((int)this.y);
		return;
	}

	
	// Attempts a displacement, or a smaller
	// one if possible. returns success or failure
	public boolean attemptDisplacement(float dx, float dy){
		boolean notCollided = false;

		//Null displacement always succeeds
		if (dx == 0 && dy == 0){return true;}

		//x only displacement
		if (dy == 0){
			if (dx>0){
				for (int ddx = (int) dx; ddx >0 ; ddx--){//Try displacements until they work
					displace(ddx,0);
					notCollided = !isCollided();
					if (notCollided){break;};
					displace(-ddx,0); // Only keep the displacement if no collision occured
				} 	
				return notCollided;
			}
			if (dx<0){
				for (int ddx = (int) dx; ddx <0 ; ddx++){//Try displacements until they work
					displace(ddx,0);
					notCollided = !isCollided();
					if (notCollided){break;};
					displace(-ddx,0); // Only keep the displacement if no collision occured
				} 	
				return notCollided;
			}
		}

		//y only displacement
		if (dx == 0){
			if (dy>0){
				for (int ddy =(int) dy; ddy >0 ; ddy--){//Try displacements until they work
					displace(0,ddy);
					notCollided = !isCollided();
					if (notCollided){break;};
					displace(0,-ddy); // Only keep the displacement if no collision occured
				} 	
				return notCollided;
			}
			if (dy<0){
				for (int ddy =(int) dy; ddy <0 ; ddy++){//Try displacements until they work
					displace(0,ddy);
					notCollided = !isCollided();
					if (notCollided){break;};
					displace(0,-ddy); // Only keep the displacement if no collision occured
				} 	
				return notCollided;
			}
		}

		//If x and y displacements occur, a success occurs if there is any
		// displacement
		boolean xAttemptSuccess = attemptDisplacement(dx,0);
		boolean yAttemptSuccess = attemptDisplacement(0,dy);

		return (xAttemptSuccess || yAttemptSuccess);

	}

}
