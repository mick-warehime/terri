package gameobjects;

import java.util.Properties;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TimedSwitch extends SwitchObject{
	private int duration;
	private long toggleTime = 0;


	public TimedSwitch(int x, int y, int w, int h, String name, TiledMap map,Properties args) throws SlickException {		
		super(x, y, w, h, name, map,args);

		// default proximity (how close in pixels to switch) is set to 5
		this.proximity = Integer.parseInt((String) args.get("prox"));
		
		// how long the switch stays on in milliseconds
		this.duration = Integer.parseInt((String) args.get("duration"))*1000;
		
	}

	public void update(){
		// if the switch is on check if it has expired
		if(state){
			long timeElapsed = getTime()-toggleTime; 
			if(timeElapsed > duration){
				toggle();
				toggleTime = 0;								
			}
		}
		
	};

	public void toggle(){
		if(!state){
			toggleTime = getTime();
		}
		state = !state;		
		target.toggle();
	}

	public void restoreTimedSwitch(){
		state = false;
	}


}
