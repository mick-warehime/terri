package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EnemyGraphics{

	protected ArrayList<Image> tileImages;
	protected Image image;
	protected Rectangle rect;

	public EnemyGraphics(Rectangle rect) throws SlickException {
		this.rect = rect;
				
		String imageString = "data/enemy1.png";
		
		image = new Image(imageString);
	}	
	
	public void render(int mapX, int mapY) {		
			image.draw(rect.getX() -mapX, rect.getY() -mapY);
	}
 

}
