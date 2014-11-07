package gameobjects;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Ladder extends GameObject {
		
		 
	
	public Ladder(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map, args);
		// TODO Auto-generated constructor stub
	}

	public boolean canCollide(){
		return false;
	}


}
