package etherable;

import java.util.Properties;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class WeightedSwitch extends SwitchObject {
	
	public WeightedSwitch(int x, int y, int w, int h, String name, TiledMap map,Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);
		// override the default game object call to get sprites and load the same sprites for every switch
		getSprites(map);
		
	}


	protected void getSprites(TiledMap map) throws SlickException{
		sprites.add(new Image("data/lever_left.png"));
	}


	public void draw(int mapX, int mapY, int mouseX, int mouseY){
		Image im = sprites.get(0);
		if(!state){
			// only draw if the switch is not weighed down
			im.draw(rect.getX()-mapX,rect.getY()-mapY);
		}

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
