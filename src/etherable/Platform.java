package etherable;

import org.newdawn.slick.tiled.TiledMap;

public class Platform extends EtherObject{

	public Platform(int i, int j, TiledMap map) {
		super(i, j, map);
	}
	
	protected void setObjectDimensions(){
		this.h = this.tileSize;
		this.w = 7*this.tileSize;
	}
}
