package etherable;

import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

public class Door extends GameObject{

	public Door(int i, int j, TiledMap map) {
		
		super(i, j, map);
		getSprites(i,j,map);

	}
	
	protected void setObjectDimensions(){
		this.h = 4*this.tileSize;
		this.w = this.tileSize;
	}
	
	public void update(int mouseX, int mouseY){
		
	};
	
	public void draw(int mapX, int mapY, int mouseX, int mouseY){			
			drawTiles((int)rect.getX(),(int)rect.getY(),mapX,mapY);
	}

	private void drawTiles(int X, int Y, int mapX, int mapY) {
		int count = 0;
		for(int x = X; x < X+w; x += tileSize){
			for(int y = Y; y < Y+h; y += tileSize){
				Image im = sprites.get(count);				
				im.draw(x-mapX,y-mapY);
				count ++;			
			}
		}
	}
}
