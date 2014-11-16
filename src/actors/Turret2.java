package actors;

import graphics.TurretGraphics;

import java.util.ArrayList;
import java.util.Properties;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import commands.CommandProvider;
import commands.GlobalInputListener;

public class Turret2 extends Actor {

	//	private Image sprite;
	private TurretBehavior behavior;
	private TurretGraphics graphics;
	
	private float angle;
	private float restingAngle;
	private float rotationSpeed;

	protected float[] rotationRange = new float[2];// [ minAngle, maxAngle]
	
	private float centerOfHubX; //Center of rotation X
	private float centerOfHubY; //Center of rotation Y
	private int chargeTime;
	private int chargeTimer;
	

	//	private float x;
	//	private float y;

	public Turret2(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {


		listener = new GlobalInputListener();
		status = new Status((float) x*map.getTileWidth(), (float) y*map.getTileHeight(), 20,20 );



		//Initialize graphics
		ArrayList<String> fileNames = new ArrayList<String>();

		// Image files to load;mount first!
		fileNames.add("data/mount.png");
		fileNames.add("data/turret.png");

		// define the relative location of the point of rotation from top left in pixels
		int centerRotateX = 20;
		int centerRotateY = 20;

		this.graphics = new TurretGraphics(status.getRect(),map,x,y,centerRotateX,centerRotateY,fileNames);

	}

	public void incorporateCollisionHandler(CollisionHandler collisionHandler) {

		status.setCollisionHandler(collisionHandler);

		behavior = new TurretBehavior(status, collisionHandler);

		engine = new TurretActionEngine(listener, status);


		listener.addProvider((CommandProvider)behavior);

	}


	//This shouldn't be necessary...? All render commands should be graphics dependent
	public void render(int mapX, int mapY, int mouseX, int mouseY){

		graphics.render(mapX, mapY,-25,-18,9);

	}


	
	
	
	class TurretActionEngine extends ActionEngine{

		
		public TurretActionEngine(GlobalInputListener listener, Status status) {
			super(listener, status);
			// TODO Auto-generated constructor stub
		}

		//No gravity or movement;
		protected void movePhysics(){};
		
		public void rotateToAngle(float targetAngle){
			// slowly move the turret towards the target angle	
			if(Math.abs(targetAngle-angle)>1){
				if(targetAngle>angle){
					angle+=rotationSpeed;
				}else
					angle-=rotationSpeed;
				
			}

			// dont let the angle go beyond the threshold angles
			if(angle > rotationRange[1]){
				angle = rotationRange[1];
			}else if (angle< rotationRange[0]){
				angle = rotationRange[0];
			}
			
		}
		
		public void chargeAndShoot(){
			
			chargeTimer +=1;

			if(chargeTimer > chargeTime){
				System.out.println("Pew!");
			}
		}

	}
	
	class TurretBehavior extends Behavior {

		private Actor currentTarget;
		
		public TurretBehavior(Status status, CollisionHandler collisionHandler) {
			super(status, collisionHandler);
		}
		
		
		
		public void determine(){
			
			setCurrentTarget();
				
			
			
			rotateToTarget();
				
			
			
			if (status.hasEffect("locked on")){
				chargeAndFire();
			}else{
				discharge();
			}

		}



		private void discharge() {
			// TODO Auto-generated method stub
			
		}



		private void chargeAndFire() {
			// TODO Auto-generated method stub
			
		}



		private void rotateToTarget() {
			// TODO Auto-generated method stub
			
		}



		private void setCurrentTarget() {
			// TODO Auto-generated method stub
			
		}

	}
	
	
}
