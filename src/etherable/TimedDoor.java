package etherable;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TimedDoor extends GameObject implements Timed{
	private boolean open = false;
	private int duration;
	private long toggleTime = 0;

	public TimedDoor(int x, int y, int w, int h, int duration, TiledMap map) throws SlickException {

		super(x, y, w, h, map);
		
		this.duration = duration*1000;

	}

	public void update(int mouseX, int mouseY){
		if(open){
			long timeElapsed = getTime()-toggleTime; 
			if(timeElapsed > duration){
				open = false;
				toggleTime = 0;
			}
		}
		
	};

	public void draw(int mapX, int mapY, int mouseX, int mouseY){			
		if(!open){
			drawTiles((int)rect.getX(),(int)rect.getY(),mapX,mapY,1);
		}
	}
	
	public void toggle(){
		open = !open;
		
		if(open){
			toggleTime = getTime();
		}
	}
	
	public boolean canCollide(){
		return !open;
	}
}
