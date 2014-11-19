package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import actors.Status;

public class EnemyGraphics{

	protected ArrayList<Image> tileImages;
	protected Image image;
	protected Image flippedImage;
	protected Status status;
//	protected Rectangle rect;

	public EnemyGraphics(Status status, String imageFile) throws SlickException {
//		this.rect = rect;
		this.status = status;

		image = new Image(imageFile);
		flippedImage = image.getFlippedCopy(true, false);
	}	

	public void render(float x, float y) {		
		
		if (status.getDirection() == 1){
			image.draw(x, y);
		}else{
			flippedImage.draw(x, y);
		}
			
		
	}


}
