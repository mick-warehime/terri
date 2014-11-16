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
	

	public ParticleBeam( int pixelX, int pixelY, int pixelLength, int pixelWidth, float angle) throws SlickException{
		super("Beam");
		
		this.angle = angle;
		this.shape = new Rectangle( pixelX, pixelY, pixelLength, pixelWidth);
		
		
//		rotate shape to angle
		float angleInRadians = (float) Math.toRadians(angle);
//		Shape newRect = (Shape) shape.transform(
//			Transform.createTranslateTransform(-pixelX, -pixelY)).transform(
//				Transform.createRotateTransform(angleInRadians)).transform(
//						Transform.createTranslateTransform(pixelX, pixelY));
		
//		this.rect =  shape.transform(Transform.createRotateTransform(angleInRadians,pixelX,pixelY));
		Shape newRect = new Rectangle(shape.getX(), shape.getY(),shape.getWidth(),shape.getHeight());
		System.out.println(newRect.getMinX() + "," + newRect.getMinY() + "|" + newRect.getMaxX() + "," + newRect.getMaxY());
		
//		newRect = (Shape) newRect.transform(
//				Transform.createTranslateTransform(-pixelX/2, -pixelY/2));
//		System.out.println(newRect.getMinX() + "," + newRect.getMinY() + "|" + newRect.getMaxX() + "," + newRect.getMaxY());
//		
//		newRect = (Shape) newRect.transform(
//				Transform.createRotateTransform((float) Math.toRadians(90),90,127)); //Rotate about an absolute point
//		System.out.println(newRect.getMinX() + "," + newRect.getMinY() + "|" + newRect.getMaxX() + "," + newRect.getMaxY());
//		
//		newRect = (Shape) newRect.transform(
//				Transform.createTranslateTransform(-pixelX/2, -pixelY/2));
//		System.out.println(newRect.getMinX() + "," + newRect.getMinY() + "|" + newRect.getMaxX() + "," + newRect.getMaxY());
//		
//		
//		newRect = (Shape) newRect.transform(
//				Transform.createTranslateTransform(pixelX, pixelY));
//		System.out.println(newRect.getMinX() + "," + newRect.getMinY() + "|" + newRect.getMaxX() + "," + newRect.getMaxY());
//		
		//Set image as a rotated beam
		this.beamImage = new Image("data/beam.png");
//		

		
		
		
		
		
		
	}
	
	
	
	public void render(int mapX, int mapY, int mouseX, int mouseY){
		
//		Image im = imagesFromFile.get(1);
		beamImage.setCenterOfRotation(0,0);
		beamImage.setRotation(angle);
		
		
		beamImage.draw(shape.getX()-mapX,shape.getY()-mapY);

		
	}

	@Override
	public void onCollisionDo(String collidingObjectClass) {
		this.isDying = true;
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

}
