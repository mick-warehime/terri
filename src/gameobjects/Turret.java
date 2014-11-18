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
	private TurretEffectiveRange effectiveRange;
	private Shape currentTarget = null;


	public Turret(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);

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
		float minX = this.shape.getMinX();
		float minY = this.shape.getMinY();

		this.effectiveRange = new TurretEffectiveRange(new Rectangle(minX-200,minY-200, 1000,1000 ), availableTargets);



	}

	public void render(int mapX, int mapY){

		graphics.render(mapX, mapY,-25,-18,angle);

	}

	private boolean isTargetable(Shape shape){
		return !collisionHandler.lineOfSightCollision(shape,this.shape);
	}
	
	private void determineCurrentTarget(){
		
		//If no current target
		if (currentTarget!= null){
			
			//Find closest targetable, shape
			if (!this.availableTargets.isEmpty()){
				
				float minDistSquared = 10000000;
				for (Shape testTarget: availableTargets){
					
					
					
					
					float targX = testTarget.getCenterX();
					float targY = testTarget.getCenterY();
					
					float testDistSquared = (float) Math.pow(targX-centerOfHubX,2);
					testDistSquared += Math.pow(targY-centerOfHubY,2);
					
					if (testDistSquared<minDistSquared){
						minDistSquared = testDistSquared;
						currentTarget = testTarget;
					}
					
				}
				
			}
			
		}
		
		
	}
	
	private float angleToPlayer(){
		// calculate the angle from the hub of the gun to the center of the player
		float centerOfPlayerX = collisionHandler.getPlayerCenterX();
		float centerOfPlayerY = collisionHandler.getPlayerCenterY();

		float answer = (float) Math.atan2(centerOfPlayerY-centerOfHubY,centerOfPlayerX-centerOfHubX);
		answer = (float) (answer*180/Math.PI);

		return answer;
	}

	private float distanceToPlayerFromHub(){
		float centerOfPlayerX = collisionHandler.getPlayerCenterX();
		float centerOfPlayerY = collisionHandler.getPlayerCenterY();

		float dx = (centerOfHubX-centerOfPlayerX);
		float dy = (centerOfHubY-centerOfPlayerY);

		return (float) Math.sqrt(Math.pow(dx,2) + Math.pow(dy, 2));
	}




	private boolean lockedOn() {
		float angleToPlayer = angleToPlayer();

		return Math.abs(angleToPlayer-angle)<2;

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

	private void tryToKillTarget(){

		chargeTimer +=1;

		if(chargeTimer > chargeTime){
			isShooting = true;
			chargeTimer = 0;
		}
	}



	public void update(){

		

		//Rotation 
		if(canTarget()){
			rotateToAngle(angleToPlayer());
		}else {
			rotateToAngle(restingAngle);
		}

		if (lockedOn()){
			tryToKillTarget();
		}else{
			chargeTimer = 0;
		}


	}

	private boolean canTarget(){
		return !collisionHandler.lineOfSightCollisionToPlayer(shape);
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

			//Determine position of muzzle
			float angleInRadians = (float) (Math.PI*angle/180);

			float dx= (float) ((float) lengthOfMuzzle*Math.cos(angleInRadians));
			int beamStartX = (int) (dx + centerOfHubX);

			float dy= (float) ((float) lengthOfMuzzle*Math.sin(angleInRadians));
			int beamStartY = (int) (dy + centerOfHubY);

			int beamLength = (int) (distanceToPlayerFromHub()- lengthOfMuzzle);
			int beamWidth = 5;

			return new ParticleBeam(beamStartX, beamStartY,  beamLength, beamWidth, angle);
		}




	}


}
