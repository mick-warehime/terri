package etherable;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class WeightedSwitch extends GameObject implements SwitchObject {
	// state  = false for up and state = true for down
	private boolean state = false;
	private GameObject target;
	private int targetX;
	private int targetY;


	public WeightedSwitch(int x, int y, int w, int h, int tx, int ty, TiledMap map) throws SlickException {		
		super(x, y, w, h, map);

		// override the default game object call to get sprites and load the same sprites for every switch
		getSprites(map);

		// location of the target
		this.targetX= tx*tileSize;
		this.targetY= ty*tileSize;
	}

	public void setTarget(ArrayList<GameObject> gameObjects){

		for(GameObject gObj : gameObjects) {
			if(gObj.getTileX()==targetX && gObj.getTileY()==targetY){	
				// give the switch the correesponding target object
				target = gObj;				
	
				
				return;
			} 
		}
		target = null;
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

	public boolean canCollide(){
		return false;
	}

	public void update(int mouseX, int mouseY){
		// if switch was not weighed down
		if(state){
//			check if it became weighed down
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

	public void toggle(){

		state = !state;		
		target.toggle();
	}

	
	private boolean isWeighedDown(){

		boolean answer =  collisionHandler.isCollidedWithPlayer(rect);
		answer = answer || collisionHandler. collidedWithEnemy(rect);
		return answer;
	}


}
