package etherable;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EtherObject {

	
	private int etherLayerId;	

	private int tileX;
	private int tileY;
	private int putX;
	private int putY;
	protected int h; 
	protected int w;
	protected int tileSize;
	

	private Rectangle rect;
	private Rectangle etherRect;
	private boolean isEther = false;
	private boolean isPut = false;
	private boolean isActive = false;
	private ArrayList<Image> sprites = new ArrayList<Image>(); 
	
	
	
	public EtherObject(int i, int j,  TiledMap map, int layerId) {
		etherLayerId = layerId;

		tileSize = map.getTileHeight();				
		
		tileX = i*tileSize;
		tileY = j*tileSize;		

		// get height/width and images
		getSprites(i,j,map);

		setObjectDimensions();
		// used for collision detection			
		rect = new Rectangle(tileX,tileY,w,h);
		etherRect = new Rectangle(tileX,tileY,w,h);
		
	}
	
	
	protected void setObjectDimensions(){
		throw new UnsupportedOperationException(); 
		
	}

//	private void setObjectDimensions(int wTiles, int hTiles){
//		this.w = wTiles*tileSize;
//		this.h = hTiles*tileSize; 
//		
//	}
	
	

	
	private void drawTiles(int X, int Y, int mapX, int mapY, float opacity) {
		int count = 0;
		for(int x = X; x < X+w; x += tileSize){
			for(int y = Y; y < Y+h; y += tileSize){
				//				
				Image im = sprites.get(count);
				im.setAlpha(opacity);
				im.draw(x-mapX,y-mapY);
				count ++;			
			}
		}
	}

	public Rectangle getEtherRect(){
		return etherRect;
	}

	public Rectangle getRect(){
		return rect;
	}

	private void getSprites(int tileI, int tileJ, TiledMap map){
	
		for(int i = tileI; i < (tileI+w/tileSize); i++){
			for(int j = tileJ; j < (tileJ+h/tileSize); j++){
				//	System.out.println(i+" "+j+" "+tileI+" "+tileJ+" "+w+" "+h);
				sprites.add(map.getTileImage(i,j,etherLayerId));
			}
		}
	}

	public boolean isActive(){
		return isActive;
	}

	

	public boolean isPut(){
		return isPut;
	}

	
	

	
	public void restore() {
		// TODO Auto-generated method stub
		isEther = false;
		isPut = false;
		isActive = false;
	
		rect.setLocation(tileX,tileY);
	}

	
	public void setObjectToEther(){
		isEther = true;	
		isActive = true;
	}

	
	public void update(int mouseX, int mouseY){
		if(isActive && !isPut){
			//		eventually used to update doors/elevators etc;
			int hoverX = (mouseX-w/2);
			int hoverY = (mouseY-h/2);
			rect.setLocation(hoverX,hoverY);			
		}		
	
	}

	
	public void draw(int mapX, int mapY, int mouseX, int mouseY){
		if(isEther){
			drawTiles(tileX,tileY,mapX,mapY,(float) 0.5);
			if(isPut){
				drawTiles(putX,putY,mapX,mapY,(float) 1);	
			}else{
				int hoverX = (mouseX-w/2+mapX);
				int hoverY = (mouseY-h/2+mapY);
	
				drawTiles(hoverX,hoverY,mapX,mapY,(float) 0.5);
			}
		}else{
			drawTiles(tileX,tileY,mapX,mapY,(float) 1);
		}		
	}

	
	public void put(int x, int y){
	
		if(isActive && !isPut){
			this.isPut = true;
			putX = x-w/2;
			putY = y-h/2;
	
			rect.setLocation(putX,putY);
		}
	}

}
