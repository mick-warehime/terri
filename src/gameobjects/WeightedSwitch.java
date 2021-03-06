package gameobjects;

import graphics.AnimatedGraphics;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class WeightedSwitch extends SwitchObject {
	
	public WeightedSwitch(int tileX, int tileY, int w, int h, String name, TiledMap map,Properties args) throws SlickException {		
		super(tileX, tileY, w, h, name, map, args);
		
		ArrayList<String> fileNames = new ArrayList<String>();
		fileNames.add("data/switch_down.png");

		fileNames.add("data/switch_up.png");
	
		this.animatedDrawer = new AnimatedGraphics(shape,map,tileX,tileY,fileNames);
		
	}

	public void update(){
		// if switch was not weighed down
		if(state){
			//check if it became weighed down
			if (!isWeighedDown()){			
				toggle();
			}
			// if the switch was weighed down check if it still is 
		}else{
			if (isWeighedDown()){			
				toggle();
			}
		}

	};


	protected boolean isWeighedDown(){

		boolean answer =  collisionHandler.isCollidedWithPlayer(shape);
		answer = answer || collisionHandler. isCollidedWithActor(shape);
		return answer;
	}


}
