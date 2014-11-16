package graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EtherEnemyGraphics{

	
	protected Image image;
	protected Rectangle rect;
	protected Rectangle etherRect;
	protected int tileHeightInPixels;
	protected int tileWidthInPixels;
	protected int tileX;
	protected int tileY;
	protected int numberOfXTiles;
	protected int numberOfYTiles;
	private int imageHeightInPixels;
	private int imageWidthInPixels;

	public EtherEnemyGraphics(Rectangle rect, Rectangle etherRect, TiledMap map, int tileX, int tileY, int numberOfXTiles, int numberOfYTiles) throws SlickException {
		this.rect = rect;
		this.etherRect = etherRect;

		this.tileX = tileX;
		this.tileY = tileY;
		this.numberOfXTiles = numberOfXTiles;
		this.numberOfYTiles = numberOfYTiles;
		
		this.tileHeightInPixels = map.getTileHeight();
		this.tileWidthInPixels = map.getTileWidth();
		this.imageWidthInPixels = numberOfXTiles*tileWidthInPixels;
		this.imageHeightInPixels = numberOfYTiles*tileHeightInPixels;

		loadTileImages(map);
	}

	private void loadTileImages(TiledMap map) throws SlickException{

		// for some reason multiple tile images load from the bottom left of the image in Tiled
		// as opposed to the topr right hence the numberOfYTiles-1 offset
		int etherIndex = map.getLayerIndex("ether");
		image = map.getTileImage(tileX,tileY+numberOfYTiles-1,etherIndex);

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
				int hoverX = (mouseX-imageWidthInPixels/2+mapX);
				int hoverY = (mouseY-imageHeightInPixels/2+mapY);
				if(canPut){
					renderImage(hoverX-mapX,hoverY-mapY,(float) 0.5);
				}
			}
		}else{
			
			renderImage((int)rect.getX()-mapX,(int)rect.getY()-mapY,(float) 1);
		}	

	}
}

	