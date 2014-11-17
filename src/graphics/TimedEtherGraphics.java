package graphics;



import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class TimedEtherGraphics extends EtherGraphics{
	

	public TimedEtherGraphics(Shape rect, Shape etherRect2, TiledMap map,
			int tileX, int tileY, int numberOfXTiles, int numberOfYTiles)
			throws SlickException {
		super(rect, etherRect2, map, tileX, tileY, numberOfXTiles, numberOfYTiles);
		
	}

	public void render(int mapX, int mapY, int mouseX, int mouseY,  boolean isEther, boolean isPut, boolean canPut, float percentTimeLeft) {
 		if(isEther){ //If ether
			//Draw ether tile
			renderTile((int)etherRect.getX(),(int)etherRect.getY(),mapX,mapY,(float) 0.5);
			//If it's placed:
			if(isPut){			
				renderTile((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) percentTimeLeft);
			}else{ //Otherwise
				int hoverX = (int) (mouseX-rect.getWidth()/2);
				int hoverY = (int) (mouseY-rect.getHeight()/2);
				if(canPut){
					renderTile(hoverX,hoverY,mapX,mapY,(float) 0.5);
				}
			}
		}else{
			renderTile((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) 1);
		}	
	}



}
