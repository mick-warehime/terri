package etherable;

import org.newdawn.slick.tiled.TiledMap;

public class Door extends EtherObject{

	public Door(int i, int j, TiledMap map, int layerId) {
		super(i, j, map, layerId);
		
	}
	
	protected void setObjectDimensions(){
		this.h = 7*this.tileSize;
		this.w = this.tileSize;
	}
}
