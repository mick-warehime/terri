package actors;

import java.util.ArrayList;

import main.CollisionHandler;

import org.newdawn.slick.geom.Rectangle;

import etherable.GameObject;

public class Status {

//	private Player player;
	private float x;
	private float y;
	private boolean isDying;
	private ArrayList<Effect> effects;

	private Rectangle rect;
	private CollisionHandler collisionHandler;

	public Status(float x, float y, CollisionHandler collisionHandler, float width, float height) {
//		this.player = player;
		this.isDying = false;
		this.x = x;
		this.y = y;
		
		rect = new Rectangle(0,0,width, height);
		
//		System.out.println("Crahs here???");
		
		rect.setX((int) x);
		rect.setY((int) y);
		
//		System.out.println("Crahs here???");
		
		this.collisionHandler = collisionHandler;
		
		effects = new ArrayList<Effect>();
		
	}
	
	public Rectangle getRect(){
		return rect;
	}

	public boolean isTouchingGround() {
		boolean answer = false;
		displace(2,'Y');
		answer = isCollided();
		displace(-2,'Y');
		
		return answer;
	}
	
	public ArrayList<GameObject> nearbyInteractives(){
		
		return collisionHandler.interactivesNearPlayer();
	}


	public float getX (){return x;}
	public float getY (){return y;}
	
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
	public void displace(float disp, char XorY){
		
		
		if (XorY == 'x' || XorY == 'X'){
			this.x += disp;
			rect.setX( this.x);
			return;
		} else if (XorY == 'y' || XorY == 'Y'){
			this.y += disp;
			rect.setY( this.y);
			return;
		}
		
		throw new UnsupportedOperationException("Improper input arguments!");
	}

	public void setDying(boolean b) {
		this.isDying = b;		
	}
	
	public boolean isDying(){
		return isDying;
	}
	
	public void update(){
		
		ArrayList <Effect> toRemove = new ArrayList<Effect>();
		
		//Count down effects and see if they've reached the end of their lifetimes
		for (Effect eff : effects){
//			System.out.println(eff.name + ", " + eff.timer);
			if (eff.countDown()){
				toRemove.add(eff);
			}
		}
		
		//Remove dead effects
		for (Effect eff: toRemove){
			effects.remove(eff);
		}
		
		return;
	}
	
	public void gainEffect(String name, int duration){
		effects.add(new Effect(name,duration));
	}
	
	public boolean hasEffect(String name){
		
		for (Effect eff: effects){
			if (eff.name == name){
				return true;
			}
		}
		return false;
	}
	
	

	class Effect{
		
		public String name;
		public int duration;
		public int timer;
		
		public Effect(String name, int duration){
			this.name = name;
			this.duration = duration;
			this.timer = duration;
		}
		
		//Count down to effect end
		public boolean countDown(){
			timer -=1;
			return (timer <= 0);
		}
		
		
	}
	
}
