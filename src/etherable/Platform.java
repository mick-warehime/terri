package etherable;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Platform extends EtherObject{

	public Platform(int x, int y, int w, int h, TiledMap map) throws SlickException {
		super(x, y, w, h, map);
	}
	
}
