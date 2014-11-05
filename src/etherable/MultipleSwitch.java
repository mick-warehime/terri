package etherable;

import java.util.Properties;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MultipleSwitch extends SwitchObject {
	
	public MultipleSwitch(int x, int y, int w, int h, String name, TiledMap map,Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);
		// override the default game object call to get sprites and load the same sprites for every switch
		getSprites(map);
		
	}


	protected void getSprites(TiledMap map) throws SlickException{
		sprites.add(new Image("data/switch_up.png"));
		sprites.add(new Image("data/switch_down.png"));
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
