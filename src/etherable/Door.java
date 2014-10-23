package etherable;

import org.newdawn.slick.tiled.TiledMap;

public class Door extends GameObject{
	private boolean open = false;
	
	public Door(int i, int j, TiledMap map) {

		super(i, j, map);
		getSprites(i,j,map);
	}

	protected void setObjectDimensions(){
		this.h = 4*this.tileSize;
		this.w = this.tileSize;
	}

	public void update(int mouseX, int mouseY){};

	public void draw(int mapX, int mapY, int mouseX, int mouseY){			
		if(!open){
			drawTiles((int)rect.getX(),(int)rect.getY(),mapX,mapY,1);
		}
	}
	
	public void toggle(){
		open = !open;
	}
	
	public boolean canCollide(){
		return open;
	}
}
