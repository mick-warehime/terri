package graphics;



import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class TimedEtherGraphics extends EtherGraphics{
	
	public TimedEtherGraphics(Rectangle rect,Rectangle etherRect, TiledMap map, int tileX, int tileY, int numberOfXTiles, int numberOfYTiles) throws SlickException {
		super(rect,etherRect,map,tileX,tileY,numberOfXTiles,numberOfYTiles);
		
	}


	public void render(int mapX, int mapY, int mouseX, int mouseY,  boolean isEther, boolean isPut, boolean canPut, float percentTimeLeft) {
 		if(isEther){ //If ether
			//Draw ether tile
			renderTile((int)etherRect.getX(),(int)etherRect.getY(),mapX,mapY,(float) 0.5);
			//If it's placed:
			if(isPut){			
				renderTile((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) percentTimeLeft);
			}else{ //Otherwise
				int hoverX = (mouseX-objectWidthInPixels/2+mapX);
				int hoverY = (mouseY-objectHeightInPixels/2+mapY);
				if(canPut){
					renderTile(hoverX,hoverY,mapX,mapY,(float) 0.5);
				}
			}
		}else{
			renderTile((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) 1);
		}	
	}



}
