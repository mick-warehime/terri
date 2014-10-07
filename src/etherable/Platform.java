package etherable;

import java.util.ArrayList;

import main.CollisionHandler;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;


// TODO 

//  add check in draw for solid/ether
//  add put draw method
//  change tile data instead of height/width give them types,
//         - type = "platform" etc that have hardcoded heights/widths

public class Platform implements Ether{
	private int etherLayerId;	

	private int tileX;
	private int tileY;

	private int putX;
	private int putY;
	private int h; 
	private int w;
	private int tileSize;
	private Rectangle rect;
	private Rectangle etherRect;
	private boolean isEther;
	private boolean isPut;
	private boolean isActive;
	private boolean isMoving;
	private ArrayList<Image> sprites = new ArrayList<Image>(); 


	public Platform( int i, int j, boolean isEther, TiledMap map, int layerId){
		etherLayerId = layerId;

		tileSize = map.getTileHeight();				

		h = tileSize;
		w = 7*tileSize;
		
		tileX = i*tileSize;
		tileY = j*tileSize;

		this.isEther = isEther;
		this.isPut = false;
		this.isActive = false;
		this.isMoving = false;

		// get height/width and images
		getSprites(i,j,map);

		// used for collision detection			
		rect = new Rectangle(tileX,tileY,w,h);
 	}

	@Override
	public void update(int mouseX, int mouseY){
		if(isActive && !isPut){
			//		eventually used to update doors/elevators etc;
			int hoverX = (mouseX-w/2);
			int hoverY = (mouseY-h/2);
			rect.setLocation(hoverX,hoverY);			
		}
	}

	@Override
	public void put(int x, int y){

		if(isActive && !isPut){
			this.isPut = true;
			putX = x-w/2;
			putY = y-h/2;

			rect.setLocation(putX,putY);
		}
	};

	@Override
	public void setObjectToEther(){
		isEther = true;	
		isActive = true;
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub
		isEther = false;
		isPut = false;
		isActive = false;

		rect.setLocation(tileX,tileY);
	}


	public boolean isCollided(Rectangle anotherRect){
		// this if statement prevents collisions occuring before the etherObj is put back
		//		System.out.println(rect.intersects(anotherRect));
		if(!isEther || isPut){
			return rect.intersects(anotherRect);
		}else{
			return false;
		}				
	}

	public boolean contains(int x,int y){
		return rect.contains(x,y);
	}

	public boolean isActive(){
		return isActive;
	}
	public boolean isPut(){
		return isPut;
	}
	public boolean isMoving(){
		return isMoving;
	}
	


	@Override
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

	@Override
	public void drawTiles(int X, int Y, int mapX, int mapY, float opacity) {

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


	private void getSprites(int tileI, int tileJ, TiledMap map){

		for(int i = tileI; i < (tileI+w/tileSize); i++){
			for(int j = tileJ; j < (tileJ+h/tileSize); j++){
				//	System.out.println(i+" "+j+" "+tileI+" "+tileJ+" "+w+" "+h);
				sprites.add(map.getTileImage(i,j,etherLayerId));
			}
		}
	}

	public Rectangle getRect(){
		return rect;
	}
	public Rectangle getEtherRect(){
		return etherRect;
	}
	@Override
	public void movingUpdate(ArrayList<Rectangle> blocks, ArrayList<Ether> eObjs){
		// TODO Auto-generated method stub
		
	}

}



