package gameobjects;


import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Door extends GameObject{
	
	protected boolean open = false;

	public Door(int x, int y, int w, int h, String name, TiledMap map,
			Properties args) throws SlickException {
		super(x, y, w, h, name, map, args);
		// TODO Auto-generated constructor stub
	}

	

	public void render(int mapX, int mapY, int mouseX, int mouseY){			
		if(!open){
			drawer.render((int)rect.getX(),(int)rect.getY(),mapX,mapY,1);
		}
	}
	
	public void toggle(){
		open = !open;
	}
	
	public boolean canCollide(){
		return !open;
	}
}
