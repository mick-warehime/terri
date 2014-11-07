package gameobjects;

import graphics.AnimatedGraphics;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class WeightedSwitch extends SwitchObject {
	
	public WeightedSwitch(int x, int y, int w, int h, String name, TiledMap map,Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);
		
		ArrayList<String> fileNames = new ArrayList<String>();
		
		fileNames.add("data/switch_up.png");
		fileNames.add("data/switch_down.png");
	
		this.animatedDrawer = new AnimatedGraphics(rect,map,tileX,tileY,fileNames);
		
	}



 

	public void update(int mouseX, int mouseY){
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


	private boolean isWeighedDown(){

		boolean answer =  collisionHandler.isCollidedWithPlayer(rect);
		answer = answer || collisionHandler. isCollidedWithEnemy(rect);
		return answer;
	}


}