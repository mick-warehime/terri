package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class AnimatedGraphics {

	protected ArrayList<Image> imagesFromFile;
	protected Shape rect;
	protected int tileHeightInPixels;
	protected int tileWidthInPixels;
	protected int tileX;
	protected int tileY; 
	
	public AnimatedGraphics(Shape rect,TiledMap map, int tileX, int tileY, ArrayList<String> fileNames) throws SlickException {
		this.rect = rect;
		
		this.tileX = tileX;
		this.tileY = tileY;
				
		this.tileHeightInPixels = map.getTileHeight();
		this.tileWidthInPixels = map.getTileWidth();
				
		loadImages( fileNames);
	}
	
	private void loadImages(ArrayList<String> fileNames) throws SlickException{
		
		imagesFromFile = new ArrayList<Image>();
						
		for(String fName : fileNames){
			imagesFromFile.add(new Image(fName));
		}
	}
	
	public void render(int mapX, int mapY, int imageIndex) {
		
		assert (imagesFromFile.get(0)!=null) : "\n ERROR: NO SPRITES DEFINED. CHECK ETHER LAYER FOR MISSING SPRITES. " +" "+ tileX+" "+tileY+"\n";
					
		Image im = imagesFromFile.get(imageIndex);

		im.draw(rect.getX()-mapX,rect.getY()-mapY);	
		
	}


}
