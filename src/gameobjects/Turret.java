package gameobjects;

import java.util.ArrayList;
import java.util.Properties;

import graphics.TurretGraphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class Turret extends GameObject implements ObjectCreator {
	// state  = false for left/not active and state = 1 for right/active
	private float angle;
	private float restingAngle;
	private float rotationSpeed;

	protected float[] rotationRange = new float[2];// [ minAngle, maxAngle]
	private TurretGraphics graphics;
	private float centerOfHubX; //Center of rotation X
	private float centerOfHubY; //Center of rotation Y
	private float lengthOfMuzzle = 63;
	private int chargeTime;
	private int chargeTimer;
	private boolean isShooting;
	private boolean isInitializing;
	private ArrayList<Shape> availableTargets;
	private CollisionDomain effectiveRange;
	private Shape currentTarget = null;


	public Turret(int tileX, int tileY, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(tileX, tileY, w, h, name, map, args);

		this.isShooting = false;
		this.isInitializing = true;

		// initial angle of the gun measure from the right (90 is down 270 is up)
		this.angle = Float.parseFloat((String) args.get("angle"));

		// allows the turret to return to its initial position
		this.restingAngle = angle;

		// how quickly the turret tracks the player; meausred in degrees/frame
		this.rotationSpeed = 2;

		this.rotationRange[0] = Float.parseFloat((String) args.get("minAngle"));
		this.rotationRange[1] = Float.parseFloat((String) args.get("maxAngle"));
		this.chargeTime = Integer.parseInt((String) args.get("chargeTime"));

		// relative location of the hub of the turret mount from top left in pixels
		centerOfHubX = shape.getX()+50;
		centerOfHubY = shape.getY()+35;

		// define the relative location of the point of rotation from top left in pixels
		int centerRotateX = 20;
		int centerRotateY = 20;

		ArrayList<String> fileNames = new ArrayList<String>();

		// mount first!
		fileNames.add("data/mount.png");
		fileNames.add("data/turret.png");


		this.graphics = new TurretGraphics(shape,map,tileX,tileY,centerRotateX,centerRotateY,fileNames);

		//Initialize targeting
		this.availableTargets = new ArrayList<Shape>();
		
		int minX = Integer.parseInt((String) args.get("minRangeX"))*map.getTileWidth();
		int minY = Integer.parseInt((String) args.get("minRangeY"))*map.getTileHeight();
		int maxX = Integer.parseInt((String) args.get("maxRangeX"))*map.getTileWidth();
		int maxY = Integer.parseInt((String) args.get("maxRangeY"))*map.getTileHeight();
		
		//This object populates the available targets for the turret
		this.effectiveRange = new CollisionDomain(new Rectangle(minX,minY, maxX-minX,maxY-minY ), availableTargets);



	}

	public void render(int mapX, int mapY){

		graphics.render(mapX, mapY,-25,-18,angle);

	}

	private boolean isTargetable(Shape shape){
		//Not blocked and within rotation range
		boolean answer = !collisionHandler.lineOfSightCollision(shape,this.shape);
		answer = answer && (angleToShape(shape)>= rotationRange[0]);
		answer = answer && (angleToShape(shape)<= rotationRange[1]);
		
		return answer;
	}

	private void determineCurrentTarget(){

		// If current target is invalid, untarget it.
		if (currentTarget != null){
			if (!isTargetable(currentTarget) 
					|| !availableTargets.contains(currentTarget))
			{ currentTarget = null;}
		}


		//If no current target, find a current target
		if (currentTarget== null){

			//Find closest, targetable shape
			if (!this.availableTargets.isEmpty()){

				float minDist = 10000000;
				for (Shape testTarget: availableTargets){

					if (!isTargetable(testTarget)){	continue;}

					float targetDist = distanceFromHub(testTarget);

					if (targetDist<minDist){
						minDist = targetDist;
						currentTarget = testTarget;
					}

				}

			}

		}


	}


	private float angleToShape(Shape shape){
		// calculate the angle from the hub of the gun to the center of the shape
		float targetX = shape.getCenterX();
		float targetY = shape.getCenterY();

		float answer = (float) Math.atan2(targetY-centerOfHubY,targetX-centerOfHubX);
		answer = (float) (answer*180/Math.PI);

		return answer;
	}
	


	private float distanceFromHub(Shape shape){
		float targetX = shape.getCenterX();
		float targetY = shape.getCenterY();

		float dx = (centerOfHubX-targetX);
		float dy = (centerOfHubY-targetY);

		return (float) Math.sqrt(Math.pow(dx,2) + Math.pow(dy, 2));
	}




	private boolean lockedOn() {
		if (currentTarget != null){

			return Math.abs(angleToShape(currentTarget)-angle)<5;
		}
		else {return false;}


	}

	private void rotateToAngle(float targetAngle){
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

	private void chargeBeamAndFire(){

		chargeTimer +=1;

		if(chargeTimer > chargeTime){
			isShooting = true;
			chargeTimer = 0;
		}
	}



	public void update(){

		determineCurrentTarget();
		
		//So you don't target killed actors or shapes leaving the effective range
		availableTargets.clear(); 
		
		//Rotation 
		if(currentTarget!=null){
			rotateToAngle(angleToShape(currentTarget));
		}else {
			rotateToAngle(restingAngle);
		}

		if (lockedOn()){
			chargeBeamAndFire();
		}else{
			chargeTimer -=1;
			if (chargeTimer <0){chargeTimer = 0;}
		}


	}



	@Override
	public boolean hasObject() {
		// TODO Auto-generated method stub
		return isShooting||isInitializing;
	}

	@Override
	public Object getObject() throws SlickException {
		if (isInitializing){
			isInitializing = false;
			return this.effectiveRange;

		}else{

			isShooting = false;

			//Determine position of muzzle in order to place the beam
			float angleInRadians = (float) (Math.PI*angle/180);

			float dx= (float) ((float) lengthOfMuzzle*Math.cos(angleInRadians));
			int beamStartX = (int) (dx + centerOfHubX);

			float dy= (float) ((float) lengthOfMuzzle*Math.sin(angleInRadians));
			int beamStartY = (int) (dy + centerOfHubY);

			int beamLength = (int) (distanceFromHub(currentTarget)- lengthOfMuzzle);
			int beamWidth = 5;
			
			currentTarget = null; //No longer target the shape after firing.

			return new ParticleBeam(beamStartX, beamStartY,  beamLength, beamWidth, angle);
		}




	}


}
