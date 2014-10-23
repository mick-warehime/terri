package etherable;

import org.newdawn.slick.tiled.TiledMap;

public class Wall extends EtherObject{

	public Wall(int i, int j, TiledMap map) {
		super(i, j, map);
		
	}
	
	protected void setObjectDimensions(){
		this.h = 7*this.tileSize;
		this.w = 2*this.tileSize;
	}
}
