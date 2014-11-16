package gameobjects;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;

public class ParticleBeam extends GameObject implements InteractiveCollideable {

	private Image image;
	private float angle;
	private boolean isDying = false;

	public ParticleBeam( int pixelX, int pixelY, int pixelLength, int pixelWidth, float angle) throws SlickException{
		super( "Beam");
		
		this.rect = new Rectangle( pixelX, pixelY, pixelLength, pixelWidth);
		this.image = new Image(pixelLength, pixelWidth, 1);
		this.angle = angle;
	}


	
	public void render(int mapX, int mapY, int mouseX, int mouseY){
		
//		Image im = imagesFromFile.get(1);
//		
		image.setRotation(angle);
		image.setCenterOfRotation(rect.getX(),rect.getY());

		image.draw(rect.getX()-mapX,rect.getY()-mapY);
		
	}

	@Override
	public void onCollisionDo(String collidingObjectClass) {
		this.isDying = true;
		
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

}
