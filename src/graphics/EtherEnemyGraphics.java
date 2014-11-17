package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EtherEnemyGraphics{

	protected ArrayList<Image> tileImages;
	protected Image image;
	protected Rectangle rect;
	protected Rectangle etherRect;
	private int imageHeightInPixels;
	private int imageWidthInPixels;

	public EtherEnemyGraphics(Rectangle rect,Rectangle etherRect) throws SlickException {
		this.rect = rect;
		this.etherRect = etherRect;				
		image = new Image("data/enemy2.png");
	}	
	
	public void render(int mapX, int mapY) {		
			image.draw(rect.getX() -mapX, rect.getY() -mapY);
	}
 
	public void renderImage(int x, int y, float opacity) {

		image.setAlpha(opacity);
		image.draw(x, y);
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
