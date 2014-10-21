package main;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;


// TODO 

//  add check in draw for solid/ether
//  add put draw method
//  change tile data instead of height/width give them types,
//         - type = "platform" etc that have hardcoded heights/widths

public class EtherObject{
	private int etherLayerId;	
	private int tileI;
	private int tileJ;
	private int putI;
	private int putJ;
	private int h; 
	private int w;
	private int tileSize;
	private Rectangle rect;
	private boolean isEther;
	private boolean isPut;
	private boolean isActive;
	private ArrayList<Image> sprites = new ArrayList<Image>(); 


	public EtherObject( int i, int j, boolean isEther, String type, TiledMap map, int layerId){
		etherLayerId = layerId;

		tileSize = map.getTileHeight();				

		this.tileI = i;
		this.tileJ = j;	

		this.isEther = isEther;
		this.isPut = false;
		this.isActive = false;

		// get height/width and images
		parseEtherType(type);
		getSprites(map);

		// used for collision detection			
		rect = new Rectangle(i*tileSize,j*tileSize,w*tileSize,h*tileSize);

	}

	

	public void update(int mouseX, int mouseY){
//		eventually used to update doors/elevators etc;
		int hoverI = (int) (mouseX-w*tileSize/2)/tileSize;
		int hoverJ = (int) (mouseY-h*tileSize/2)/tileSize;
		rect.setLocation(hoverI*tileSize,hoverJ*tileSize);
	}
	
	
	public void put(int x, int y){

		if(isEther && !isPut){
			this.isPut = true;
			putI = (int) (x-w*tileSize/2)/tileSize;
			putJ = (int) (y-h*tileSize/2)/tileSize;

			rect.setLocation(putI*tileSize,putJ*tileSize);
		}
		
	};

	
	public void setObjectToEther(){
		isEther = true;	
		isActive = true;
	}

	
	public void restore() {
		// TODO Auto-generated method stub
		isEther = false;
		isPut = false;
		isActive = false;
		
		rect.setLocation(tileI*tileSize,tileJ*tileSize);
		
	}

	private void parseEtherType(String type){
		if(type.equals("platform")){
			this.h = 1;
			this.w = 7;
		}		
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
	

	
	public void draw(int mapX, int mapY, int mouseX, int mouseY){
		if(isEther){
			drawTiles(tileI,tileJ,mapX,mapY,(float) 0.5);
			if(isPut){
				drawTiles(putI,putJ,mapX,mapY,(float) 1);	
			}else{
				int hoverI = (int) (mouseX-w*tileSize/2+mapX)/tileSize;
				int hoverJ = (int) (mouseY-h*tileSize/2+mapY)/tileSize;

				drawTiles(hoverI,hoverJ,mapX,mapY,(float) 0.5);
			}
		}else{
			drawTiles(tileI,tileJ,mapX,mapY,(float) 1);
		}		
	}

	
	public void drawTiles(int I, int J, int mapX, int mapY, float opacity) {

		int count = 0;
		for(int i = I; i < (I+w); i++){
			for(int j = J; j < (J+h); j++){
				//				System.out.println(i+" "+j);
				Image im = sprites.get(count);
				im.setAlpha(opacity);
				im.draw((i)*tileSize-mapX,j*tileSize-mapY);
				count ++;			
			}
		}
	}	


	private void getSprites(TiledMap map){

		for(int i = tileI; i < (tileI+w); i++){
			for(int j = tileJ; j < (tileJ+h); j++){
				//								System.out.println(i+" "+j+" "+tileI+" "+tileJ+" "+w+" "+h);
				sprites.add(map.getTileImage(i,j,etherLayerId));
			}
		}
	}

	public Rectangle getRect(){
		return rect;
	}
	
}



