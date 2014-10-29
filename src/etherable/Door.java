package etherable;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Door extends GameObject{
	private boolean open = false;
	
	public Door(int x, int y, int w, int h, TiledMap map) throws SlickException {

		super(x, y, w, h, map);
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
		return !open;
	}
}
