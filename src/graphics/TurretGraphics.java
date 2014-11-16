package graphics;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class TurretGraphics {

	protected ArrayList<Image> imagesFromFile;
	protected Shape shape;
	protected int tileHeightInPixels;
	protected int tileWidthInPixels;
	protected int tileX;
	protected int tileY; 
	private int centerRotateX;
	private int centerRotateY;
	
	public TurretGraphics(Shape rect2,TiledMap map, int tileX, int tileY, int centerRotateX, int centerRotateY, ArrayList<String> fileNames) throws SlickException {
		this.shape = rect2;
		
		this.tileX = tileX;
		this.tileY = tileY;
		
		this.centerRotateX = centerRotateX;
		this.centerRotateY = centerRotateY;
		
		this.tileHeightInPixels = map.getTileHeight();
		this.tileWidthInPixels = map.getTileWidth();
				
		loadImages(map, fileNames);

	}
	
	private void loadImages(TiledMap map, ArrayList<String> fileNames) throws SlickException{
		// image index 0 should be the mount and image index 1 should be the gun
		imagesFromFile = new ArrayList<Image>();
						
		for(String fName : fileNames){
			imagesFromFile.add(new Image(fName));
		}
				
		assert (imagesFromFile.get(0)!=null) : "\n ERROR: NO SPRITES DEFINED. CHECK ETHER LAYER FOR MISSING SPRITES. " +" "+ tileX+" "+tileY+"\n";


		// set the rotation point of the gun
		
	}
	
	public void renderMount(int mapX, int mapY) {
		
					
		Image im = imagesFromFile.get(0);

		im.draw(shape.getX()-mapX,shape.getY()-mapY);	
		
	}
	
	public void render(int mapX, int mapY, int offCenterX, int offCenterY, float angle) {
		
		renderMount(mapX, mapY);	
 					
		Image im = imagesFromFile.get(1);
		
		im.setRotation(angle);
		im.setCenterOfRotation(centerRotateX,centerRotateY);

		im.draw(shape.getX()-mapX-offCenterX,shape.getY()-mapY-offCenterY);	
		

	}
	
	


}
