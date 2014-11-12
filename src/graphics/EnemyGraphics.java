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
	protected int tileHeightInPixels;
	protected int tileWidthInPixels;
	protected int tileX;
	protected int tileY;
	protected int numberOfXTiles;
	protected int numberOfYTiles;
	
	public EnemyGraphics(Rectangle rect,TiledMap map, int tileX, int tileY, int numberOfXTiles, int numberOfYTiles) throws SlickException {
		this.rect = rect;
		
		this.tileX = tileX;
		this.tileY = tileY;
		this.numberOfXTiles = numberOfXTiles;
		this.numberOfYTiles = numberOfYTiles;
		
		this.tileHeightInPixels = map.getTileHeight();
		this.tileWidthInPixels = map.getTileWidth();
		
		
		loadTileImages(map);
	}
	
	private void loadTileImages(TiledMap map) throws SlickException{
		
		// for some reason multiple tile images load from the bottom left of the image in Tiled
		// as opposed to the topr right hence the numberOfYTiles-1 offset
		int etherIndex = map.getLayerIndex("ether");
		image = map.getTileImage(tileX,tileY+numberOfYTiles-1,etherIndex);
		
	}
	
	public void render(int mapX, int mapY) {		
			image.draw(rect.getX() -mapX, rect.getY() -mapY);
	}
 

 

}
