package actors;

import java.util.ArrayList;
import java.util.Iterator;

import main.CollisionHandler;

import org.newdawn.slick.geom.Rectangle;

import gameobjects.GameObject;

public class Status {

	private boolean isDying;
	private ArrayList<Effect> effects;
//	private ArrayList<Attribute> attributes;

	private Rectangle rect;
	private CollisionHandler collisionHandler;

	public Status(float x, float y, float width, float height) {
		//		this.player = player;
		this.isDying = false;

		rect = new Rectangle((int) x,(int) y,width, height);


		effects = new ArrayList<Effect>();
//		attributes = new ArrayList<Attribute>();
//		attributes.add(new Attribute("Alive", true));

	}

	public Rectangle getRect(){
		return rect;
	}

	public void setCollisionHandler(CollisionHandler collisionHandler){
		this.collisionHandler = collisionHandler;
	}



	public boolean isTouchingGround() {
		boolean answer = false;
		displace(2,'Y');
		answer = isCollided();
		displace(-2,'Y');


		return answer;
	}



	public ArrayList<GameObject> nearbyInteractives(){

		return collisionHandler.interactiveObjectsNearRect(this.rect);
	}


	public float getX (){return rect.getX();}
	public float getY (){return rect.getY();}


	public boolean isCollided(){
		if(!hasEffect("ethered")){
			return collisionHandler.isCollided(rect);
		}
		return false;
	}

	//Displaces the player 
	public void displace(float disp, char XorY){

		if(!hasEffect("ethered")){
			if (XorY == 'x' || XorY == 'X'){
				float newX = rect.getX() + disp;
				rect.setX( newX);
				return;
			} else if (XorY == 'y' || XorY == 'Y'){
				float newY = rect.getY() + disp;
				rect.setY(newY);
				return;
			}
		}
//		throw new UnsupportedOperationException("Improper input arguments!");
	}


	public void setAlive(){
		this.isDying = false;
	}

	public void setX(float x){
		this.rect.setX(x);
	}

	public void setY(float y){
		// the -1 makes sure he doesnt start stuck in anything
		this.rect.setY(y-1);
	}

	public void setDying(boolean b) {
		this.isDying = b;		
	}

	public boolean isDying(){
		return isDying;
	}

	public void updateEffects(){


		boolean touchingLadder = false;

		//count down on each effect, remove ones that have run down
		for (Iterator<Effect> iterator = effects.iterator(); iterator.hasNext();) {
			Effect eff = iterator.next();
			if (eff.name.equals("touching ladder")){ touchingLadder = true;}	

			if (eff.countDown()){
				// Remove the current element from the iterator and the list.
				iterator.remove();
			}
		}

		if (!touchingLadder){removeEffect("climbing");}


		return;
	}


	void removeEffect(String name){
		//Iterate over all effect's elements and remove
		for (Iterator<Effect> iterator = effects.iterator(); iterator.hasNext();) {
			Effect eff = iterator.next();
			if(eff.name.equals(name)){
				// Remove the current element from the iterator and the list.
				iterator.remove();
			}
		}
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


		
//	class Attribute{
//		public String name;
//		private float value;
//		
//		public Attribute(String name, float initValue){
//			this.name = name;
//			this.value = initValue;
//		}
//		
//		public float getValue(){
//			return value;
//		}
//		
//		public void setValue(float newValue){
//			this.value = newValue;
//		}
//	}
	
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
