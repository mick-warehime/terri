package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class TileGraphics{

	public ArrayList<Image> tileImages;
	protected Shape rect;
	protected int tileHeightInPixels;
	protected int tileWidthInPixels;
	protected int tileX;
	protected int tileY;
	protected int numberOfXTiles;
	protected int numberOfYTiles;
	
	public TileGraphics(Shape rect2,TiledMap map, int tileX, int tileY, int numberOfXTiles, int numberOfYTiles) throws SlickException {
		this.rect = rect2;
		
		this.tileX = tileX;
		this.tileY = tileY;
		
		this.numberOfXTiles = numberOfXTiles;
		this.numberOfYTiles = numberOfYTiles;
		
		this.tileHeightInPixels = map.getTileHeight();
		this.tileWidthInPixels = map.getTileWidth();
		
		
		loadTileImages(map);
	}
	
	private void loadTileImages(TiledMap map) throws SlickException{
		
		tileImages = new ArrayList<Image>();
		
		int etherIndex = map.getLayerIndex("ether");
		
		for(int i = 0; i < numberOfXTiles; i++){
			for(int j = 0; j < numberOfYTiles; j++){
				tileImages.add(map.getTileImage(i + tileX,j + tileY,etherIndex));
			}
		}
		
		
	}
	
	public void render(int mapX, int mapY, float opacity) {

		assert (tileImages.get(0)!=null) : "\n ERROR: NO SPRITES DEFINED. CHECK ETHER LAYER FOR MISSING SPRITES. " +" "+ tileX+" "+tileY+"\n";
					
		int count = 0;
		for(int i = 0; i < numberOfXTiles; i++){
			for(int j = 0; j < numberOfYTiles; j++){
				Image im = tileImages.get(count);
				im.setAlpha(opacity);
				//		
				im.draw(rect.getX() + i*tileWidthInPixels - mapX,
									       rect.getY() + j*tileHeightInPixels - mapY);
				count ++;			
			}
		}
		
	}
	
	public void render(int topLeftX, int topLeftY, int mapX, int mapY, float opacity) {
		
		assert (tileImages.get(0)!=null) : "\n ERROR: NO SPRITES DEFINED. CHECK ETHER LAYER FOR MISSING SPRITES. " +" "+ tileX+" "+tileY+"\n";
					
		int count = 0;
		for(int i = 0; i < numberOfXTiles; i++){
			for(int j = 0; j < numberOfYTiles; j++){
				//		
				Image im = tileImages.get(count);
				im.setAlpha(opacity);
				im.draw(topLeftX + i*tileWidthInPixels -mapX,
						topLeftY + j*tileHeightInPixels-mapY);
				count ++;			
			}
		}
	}

 

}
