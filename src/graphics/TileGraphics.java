package graphics;



import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class TileGraphics{
	
	private Image[][] tileImages;
	protected Shape rect;
	protected int tileHeightInPixels;
	protected int tileWidthInPixels;
	protected int numberOfXTiles;
	protected int numberOfYTiles;
	
	public TileGraphics(Shape rect,TiledMap map, int tileX, int tileY, int numberOfXTiles, int numberOfYTiles) throws SlickException {
		this.rect = rect;
		
		
		this.numberOfXTiles = numberOfXTiles;
		this.numberOfYTiles = numberOfYTiles;
		
		
		
		this.tileHeightInPixels = map.getTileHeight();
		this.tileWidthInPixels = map.getTileWidth();
		
		
		loadTileImages(map, tileX, tileY);
	}
	
	private void loadTileImages(TiledMap map, int tileX, int tileY) throws SlickException{
		

		tileImages = new Image[numberOfXTiles][numberOfYTiles];
		
		int etherIndex = map.getLayerIndex("ether");
		
		for(int i = 0; i < numberOfXTiles; i++){
			for(int j = 0; j < numberOfYTiles; j++){
				
				tileImages[i][j] = map.getTileImage(i + tileX,j + tileY,etherIndex);
			}
		}
		
		
	}
	
	public void render(int mapX, int mapY, float opacity) {

		assert (tileImages!=null) : "\n ERROR: NO SPRITES DEFINED. CHECK ETHER LAYER FOR MISSING SPRITES. " ;
					
		
		for(int i = 0; i < numberOfXTiles; i++){
			for(int j = 0; j < numberOfYTiles; j++){
				
				Image im = tileImages[i][j];
				im.setAlpha(opacity);
					
				im.draw(rect.getX() + i*tileWidthInPixels - mapX,
						rect.getY() + j*tileHeightInPixels - mapY);
			}
		}
		
	}
	
	public void setRect(Shape rect){
		
		this.rect = rect;
		
	}
	
	public void renderTile(int topLeftX, int topLeftY, int mapX, int mapY, float opacity) {
		
		assert (tileImages!=null) : "\n ERROR: NO SPRITES DEFINED. CHECK ETHER LAYER FOR MISSING SPRITES. ";		
		
		for(int i = 0; i < numberOfXTiles; i++){
			for(int j = 0; j < numberOfYTiles; j++){	
				Image im = tileImages[i][j];
				im.setAlpha(opacity);
				im.draw(topLeftX + i*tileWidthInPixels -mapX,
						topLeftY + j*tileHeightInPixels-mapY);		
			}
		}
	}
	
	public void rotateImages(float rotationAngle){
		
		//Number of right angle turns
		int numTurns = (int) Math.round(rotationAngle / (Math.PI*0.5));
		//Rounds rotationAngle to nearest integer multiple of pi/2
		float testAngle = (float) ((Math.PI*0.5)*numTurns);
		
		assert (Math.abs(testAngle-rotationAngle)<0.001): "Error! rotation angles for TileGraphics must be integer multiples of pi/2!";
		
		//pi/2 * 4 = 2 pi, i.e. no rotation. Account for remainder	
		if ((numTurns -1) % 4 == 0){ 
			/* pi/2 rotation. 
			 * x (row indices) -> (column indices)
			 * y (column indices) -> (row indices, backwards)
			 * */
			 
			Image[][] temp = new Image[numberOfYTiles][numberOfXTiles];
			for (int i = 0; i<numberOfXTiles; i++){
				for (int j = 0; j<numberOfYTiles; j++){
					temp[j][numberOfXTiles-(i+1)] = tileImages[i][j];
				}	
			}
			
			int tempY = numberOfYTiles;
			numberOfYTiles = numberOfXTiles;
			numberOfXTiles = tempY;
			
			tileImages = temp;
			
		}
		
		if ( (numTurns -2) % 4 == 0){ 
			/* pi rotation. 
			 * x (row indices) -> (row indices, backwards)
			 * y (column indices) -> (column indices, backwards)
			 * */
			
			 
			Image[][] temp = new Image[numberOfXTiles][numberOfYTiles];
			for (int i = 0; i<numberOfXTiles; i++){
				for (int j = 0; j<numberOfYTiles; j++){
					temp[numberOfXTiles-(i+1)][numberOfYTiles-(j+1)] = tileImages[i][j];
					
				}	
			}
			
			
			tileImages = temp;
			
		}
		
		if ( (numTurns -3) % 4 == 0){ 
			/* -pi/2 rotation. 
			 * x (row indices) -> (column indices, backwards)
			 * y (column indices) -> (row indices)
			 * */
			
			 
			Image[][] temp = new Image[numberOfYTiles][numberOfXTiles];
			for (int i = 0; i<numberOfXTiles; i++){
				for (int j = 0; j<numberOfYTiles; j++){
					temp[numberOfYTiles-(j+1)][i] = tileImages[i][j];
					
				}	
			}
			
			int tempY = numberOfYTiles;
			numberOfYTiles = numberOfXTiles;
			numberOfXTiles = tempY;
			
			tileImages = temp;
			
		}
		
		
	}

 

}
