package gameobjects;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MultiWeightedSwitch extends WeightedSwitch {
	
	private int index;
	
	public MultiWeightedSwitch(int x, int y, int w, int h, String name, TiledMap map,Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);
		
		this.index = Integer.parseInt((String) args.get("index"));
		
	}

	public void toggle(){
		state = !state;		
		// tell the target which switch was activated/deactivated
		target.toggle(index);
	}


}
