package graphics;



import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class EtherGraphics extends TileGraphics{


	protected Shape etherRect;
	protected int objectWidthInPixels;
	protected int objectHeightInPixels;
	

	public EtherGraphics(Shape rect,Shape etherRect2, TiledMap map, int tileX, int tileY, int numberOfXTiles, int numberOfYTiles) throws SlickException {
		super(rect,map,tileX,tileY,numberOfXTiles,numberOfYTiles);
		
		this.etherRect = etherRect2;

		this.objectWidthInPixels = numberOfXTiles*map.getTileWidth();
		this.objectHeightInPixels = numberOfYTiles*map.getTileHeight();
		
	}






	public void renderTile(int topLeftX, int topLeftY, int mapX, int mapY, float opacity) {
 		super.render(topLeftX, topLeftY, mapX, mapY, opacity);
	}

	public void render(int mapX, int mapY, int mouseX, int mouseY,  boolean isEther, boolean isPut, boolean canPut, float putOpacity) {
 		if(isEther){ //If ether
			//Draw ether tile
			renderTile((int)etherRect.getX(),(int)etherRect.getY(),mapX,mapY,(float) 0.5);
			//If it's placed:
			if(isPut){			
				renderTile((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) putOpacity);
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
