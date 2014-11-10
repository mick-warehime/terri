package gameobjects;

import java.util.ArrayList;
import java.util.Properties;

import graphics.TurretGraphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Turret extends GameObject {
	// state  = false for left/not active and state = 1 for right/active
	protected float angle;
	protected float[] rotationRange = new float[2];// [ minAngle, maxAngle]
	private TurretGraphics graphics;
	private float centerOfHubX;
	private float centerOfHubY;
	private int chargeTime;
	private int chargeTimer;
	public boolean onTarget;


	public Turret(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);

		// onTarget keeps track of whether or not the gun can see the player

		// initial angle of the gun measure from the right (90 is down 270 is up)
		this.angle = Float.parseFloat((String) args.get("angle"));

		this.rotationRange[0] = Float.parseFloat((String) args.get("minAngle"));
		this.rotationRange[1] = Float.parseFloat((String) args.get("maxAngle"));

		this.chargeTime = Integer.parseInt((String) args.get("chargeTime"));

		// relative location of the hub of the turret mount from top left in pixels
		centerOfHubX = rect.getX()+50;
		centerOfHubY = rect.getY()+35;

		// define the relative location of the point of rotation from top left in pixels
		int centerRotateX = 20;
		int centerRotateY = 20;

		ArrayList<String> fileNames = new ArrayList<String>();

		// mount first!
		fileNames.add("data/mount.png");
		fileNames.add("data/turret.png");



		this.graphics = new TurretGraphics(rect,map,tileX,tileY,centerRotateX,centerRotateY,fileNames);
	}

	public void draw(int mapX, int mapY, int mouseX, int mouseY){

		graphics.render(mapX, mapY,-25,-18,angle);

	}

	private void getAngle(){
		// calculate the angle from the hub of the gun to the center of the player
		float centerOfPlayerX = collisionHandler.getPlayerCenterX();
		float centerOfPlayerY = collisionHandler.getPlayerCenterY();

		angle = (float) Math.atan2(centerOfPlayerY-centerOfHubY,centerOfPlayerX-centerOfHubX);
		angle = (float) (angle*180/Math.PI);

		// dont let the angle go beyond the threshold angles
		if(angle > rotationRange[1]){
			angle = rotationRange[1];
		}else if (angle < rotationRange[0]){
			angle = rotationRange[0];
		}
	}
	private void updateChargeTimer(){


		chargeTimer += 1;

	}

	public void update(int mouseX, int mouseY){

//		System.out.println(chargeTimer);
		updateChargeTimer();

		getAngle();


	};
}
