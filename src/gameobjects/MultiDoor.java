package gameobjects;


import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import actors.Enemy;

public class MultiDoor extends Door{
	
	// all must be true to open the door
	private ArrayList<Boolean> switchStates = new ArrayList<Boolean>();
	private int numberOfLocks;
	
	public MultiDoor(int x, int y, int w, int h, String name, TiledMap map,
			Properties args) throws SlickException {
		super(x, y, w, h, name, map, args);
		
		// must match number of multiSwitches on the map 
		this.numberOfLocks = Integer.parseInt((String) args.get("numberOfLocks"));
		
		// initialize the list of locks
		for(int i = 0; i <numberOfLocks; i++){
			switchStates.add(false);
		}
	}

	

	public void update(int mouseX, int mouseY){
		// loop over all switches if one is locked then keep door closed 
		open = false;
		for(Boolean isOpen: switchStates){
			if(!isOpen){
				return;
			}
		}
		// if they are all open the door is set to open
		open = true;
		
	};
	
	public void toggle(int switchNumber){
		switchStates.set(switchNumber, !switchStates.get(switchNumber));
	}
	
}
