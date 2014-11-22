package graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import actors.Status;

public class PlayerGraphics extends ActorGraphics {

	private Image flameImage;
	
	
	
	public PlayerGraphics(Status status, String imageFile)
			throws SlickException {
		super(status, imageFile);
		
		flameImage = new Image("data/thrusterFlame.png");
		
		
	}
	
	@Override
	public void render( int renderX, int renderY){
		super.render(renderX, renderY);
		if (status.hasEffect("hovering")){
			float dy = status.getRect().getHeight();			
			flameImage.draw(renderX,renderY+dy);
		}
		
	}

	

}
