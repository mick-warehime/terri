package gameobjects;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class ParticleBeam extends GameObject implements InteractiveCollideable {

	private Image beamImage;
	private float angle;
	private boolean isDying = false;
	private int lifeTime = 40;
	private int lifeCounter= 0;
	private int length;
	private int width;
	

	public ParticleBeam( int pixelX, int pixelY, int pixelLength, int pixelWidth, float angle) throws SlickException{
		super("Beam");
		
		this.angle = angle;
		this.length = pixelLength;
		this.width = pixelWidth;
		
		float angleInRadians = (float) Math.toRadians(angle);
		
		//The last two arguments of createRotateTransform set the rotation 
		//center in absolute coordinates
		this.shape = new Rectangle( pixelX, pixelY, pixelLength, pixelWidth).transform(
				Transform.createRotateTransform(angleInRadians,pixelX,pixelY)); 
		

		//Set image as a rotated beam
		this.beamImage = new Image("data/beam.png");
//		

		
		
		
		
		
		
	}
	
	
	
	public void render(int mapX, int mapY){
		
//		Image im = imagesFromFile.get(1);
		beamImage.setCenterOfRotation(0,(float) 2.5);
		beamImage.setRotation(angle);
		
		float pixelX = shape.getCenterX();
		float pixelY = shape.getCenterY();
		beamImage.draw(pixelX-mapX,pixelY-mapY);

		
	}

	@Override
	public void onCollisionDo(String collidingObjectClass) {
//		this.isDying = true;
		System.out.println("Collisino!");
		
	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(String collidingObjectClass) {
		// TODO Auto-generated method stub
		return new ArrayList<Command>();
	}


	@Override
	public Shape getRect() {
		// TODO Auto-generated method stub
		return shape;
	}
	
	public boolean isDying(){
		return isDying;
	}
	
	public void update(){
//		System.out.println("Life counter:" + lifeCounter);
//		System.out.println(shape);
		lifeCounter +=1;
		if (lifeCounter == lifeTime){
			isDying = true;
		}
	}

	public boolean canCollide(){
		return false;
	}
}
