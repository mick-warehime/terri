package gameobjects;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TimedPlatform extends EtherObject  implements Timed{

	private int duration;
	private long putTime;

	public TimedPlatform(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map,args);

		// default duration is set to 1000 milliseconds	
		this.duration = Integer.parseInt((String) args.get("duration"))*1000;

	}



	@Override
	public void update(int mouseX, int mouseY){
		super.update(mouseX, mouseY);
		if(isPut){
			long timeElapsed = getTime()-putTime; 
			if(timeElapsed > duration){
				this.restore();
			}
		}
	}

	public void put(int x, int y){
		super.put(x, y);

		putTime = getTime();
	}


}
