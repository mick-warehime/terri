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
		this.rect = new Rectangle( pixelX, pixelY, pixelLength, pixelWidth);
		
		
//		rotate rect to angle
		float angleInRadians = (float) Math.toRadians(angle);
//		Shape newRect = (Shape) rect.transform(
//			Transform.createTranslateTransform(-pixelX, -pixelY)).transform(
//				Transform.createRotateTransform(angleInRadians)).transform(
//						Transform.createTranslateTransform(pixelX, pixelY));
		
//		this.rect =  rect.transform(Transform.createRotateTransform(angleInRadians,pixelX,pixelY));
		Shape newRect = new Rectangle(rect.getX(), rect.getY(),rect.getWidth(),rect.getHeight());
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
		
		
		beamImage.draw(rect.getX()-mapX,rect.getY()-mapY);

		
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
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return rect;
	}
	
	public boolean isDying(){
		return isDying;
	}
	
	public void update(){
//		System.out.println("Life counter:" + lifeCounter);
//		System.out.println(rect);
		lifeCounter +=1;
		if (lifeCounter == lifeTime){
			isDying = true;
		}
	}

}
