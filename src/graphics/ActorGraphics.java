package graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import actors.Status;

public class ActorGraphics {

	protected Image image;
	protected Image flippedImage;
	protected Status status;

	public ActorGraphics(Status status, String imageFile) throws SlickException {
		this.status = status;

		image = new Image(imageFile);
		flippedImage = image.getFlippedCopy(true, false);
	}

	public void render(int renderX, int renderY) {		
		
		if (status.getDirection() == 1){
			image.draw(renderX, renderY);
		}else{
			flippedImage.draw(renderX, renderY);
		}
			
		
	}

	
}