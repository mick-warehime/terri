package actors;

import java.util.ArrayList;

import main.CollisionHandler;

import org.newdawn.slick.geom.Rectangle;

import etherable.GameObject;

public class PlayerStatus {

//	private Player player;
	private float x;
	private float y;

	private Rectangle rect;
	private CollisionHandler collisionHandler;

	public PlayerStatus(float x, float y, CollisionHandler collisionHandler) {
//		this.player = player;

		this.x = x;
		this.y = y;
		
		rect = new Rectangle(1,1,31,31);
		
//		System.out.println("Crahs here???");
		
		rect.setX((int) x);
		rect.setY((int) y);
		
//		System.out.println("Crahs here???");
		
		this.collisionHandler = collisionHandler;
		this.collisionHandler.addPlayerRect(rect);
		
		
	}
	
	public Rectangle getRect(){
		return rect;
	}

	public boolean isTouchingGround() {
		boolean answer = false;
		displace(0,+2);
		answer = isCollided();
		displace(0,-2);
		
		return answer;
	}
	
	public ArrayList<GameObject> nearbyInteractives(){
		
		return collisionHandler.interactivesNearPlayer();
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

	
	
}
