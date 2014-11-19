package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import actors.Status;

public class EtherEnemyGraphics extends EnemyGraphics{

	protected ArrayList<Image> tileImages;
	
	protected Rectangle rect;
	protected Rectangle etherRect;
	

	public EtherEnemyGraphics(Status status, Rectangle etherRect, String imageFile) throws SlickException {
		super(status,imageFile);
		this.rect = status.getRect();
		this.etherRect = etherRect;				
		
	}	
	
	private void renderImage(int x, int y, float opacity) {

		image.setAlpha(opacity);
		flippedImage.setAlpha(opacity);
		super.render(x, y);
	}
	public void render(int mapX, int mapY, int mouseX, int mouseY, boolean isEther, boolean isPut, boolean canPut) {

		if(isEther){ //If ether
			//Draw ether tile
			renderImage((int)etherRect.getX()-mapX,(int)etherRect.getY()-mapY,(float) 0.5);
			//If it's placed:
			if(isPut){			
				renderImage((int)rect.getX()-mapX,(int)rect.getY()-mapY,(float) 1);
			}else{ //Otherwise
				int hoverX = (int) (mouseX-rect.getWidth()/2);
				int hoverY = (int) (mouseY-rect.getHeight()/2);
				if(canPut){
					renderImage(hoverX-mapX,hoverY-mapY,(float) 0.5);
				}
			}
		}else{
			
			renderImage((int)rect.getX()-mapX,(int)rect.getY()-mapY,(float) 1);
		}	

	}
}
