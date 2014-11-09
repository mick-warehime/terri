package gameobjects;

import java.util.ArrayList;
import java.util.Properties;

import graphics.TurretGraphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Turret extends GameObject {
	// state  = false for left/not active and state = 1 for right/active
	protected float angle;
	protected float[] rotationRange;// [ minAngle, maxAngle]
	private TurretGraphics graphics;
	private float centerOfHubX;
	private float centerOfHubY;

	public Turret(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);
		
		// initial angle of the gun measure from the right (90 is down 270 is up)
		this.angle = Float.parseFloat((String) args.get("angle"));
		
//		this.rotationRange[0] = Float.parseFloat((String) args.get("minAngle"));
//		this.rotationRange[1] = Float.parseFloat((String) args.get("maxAngle"));
		
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

	
	
	public void update(int mouseX, int mouseY){
				
		// calculate the angle from the hub of the gun to the center of the player
		float centerOfPlayerX = collisionHandler.getPlayerCenterX();
		float centerOfPlayerY = collisionHandler.getPlayerCenterY();
		
		angle = (float) Math.atan2(centerOfPlayerY-centerOfHubY,centerOfPlayerX-centerOfHubX);
		angle = (float) (angle*180/Math.PI);
		
		// see if you should make a new bullet
//		if(canShoot){
//			
//			
//		}
		
		
	};
}
