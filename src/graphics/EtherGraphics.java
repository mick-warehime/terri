package graphics;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class EtherGraphics extends TileGraphics{


	
	private TileGraphics originalGraphics; //draws in the original position
	
	private float rotatedAngle;

	public EtherGraphics(Shape rect,Shape etherRect, TiledMap map, int tileX, int tileY, int numberOfXTiles, int numberOfYTiles) throws SlickException {
		super(rect,map,tileX,tileY,numberOfXTiles,numberOfYTiles);
		
		this.originalGraphics = new TileGraphics(etherRect,map,tileX,tileY,numberOfXTiles,numberOfYTiles);
		this.rotatedAngle = 0;
		
		
	}
	
	
	
	public void rotateImages(float rotationAngle){
		super.rotateImages(rotationAngle);
		this.rotatedAngle+= rotationAngle;
		
	}




	public void render(int mapX, int mapY, int mouseX, int mouseY,  boolean isEther, boolean isPut, boolean canPut, float putOpacity) {
 		
		if(isEther){ //If ether
			//Draw ether tile
			originalGraphics.render(mapX, mapY, (float) 0.5);
			//If it's placed:
			if(isPut){			
				render(mapX, mapY, putOpacity);
			}else{ //Otherwise
				int hoverX = (int) (mouseX-rect.getWidth()/2);
				int hoverY = (int) (mouseY-rect.getHeight()/2);
				if(canPut){
					renderTile(hoverX,hoverY,mapX,mapY,(float) 0.5);
				}
			}
		}else{
			render(mapX, mapY, (float) 1); 
		}	
	}


	public void restoreOriginalAngle() {
		rotateImages(-this.rotatedAngle);
	}



}
