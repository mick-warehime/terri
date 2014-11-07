package graphics;



import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EtherGraphics extends TileGraphics{


	protected Rectangle etherRect;
	protected int objectWidthInPixels;
	protected int objectHeightInPixels;
	

	public EtherGraphics(Rectangle rect,Rectangle etherRect, TiledMap map, int tileX, int tileY, int numberOfXTiles, int numberOfYTiles) throws SlickException {
		super(rect,map,tileX,tileY,numberOfXTiles,numberOfYTiles);
		
		this.etherRect = etherRect;

		this.objectWidthInPixels = numberOfXTiles*map.getTileWidth();
		this.objectHeightInPixels = numberOfYTiles*map.getTileHeight();
		
	}



	public void renderTile(int topLeftX, int topLeftY, int mapX, int mapY, float opacity) {
 		super.render(topLeftX, topLeftY, mapX, mapY, opacity);
	}

	public void render(int mapX, int mapY, int mouseX, int mouseY,  boolean isEther, boolean isPut, boolean canPut) {
 		if(isEther){ //If ether
			//Draw ether tile
			renderTile((int)etherRect.getX(),(int)etherRect.getY(),mapX,mapY,(float) 0.5);
			//If it's placed:
			if(isPut){			
				renderTile((int)rect.getX(),(int)rect.getY(),mapX,mapY,(float) 1);
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
