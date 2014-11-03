package etherable;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Switch extends GameObject implements Interactive{
	// state  = 1 for up and state = 2 for down
	private boolean state = false;
	private GameObject target;
	private int targetX;
	private int targetY;

	public Switch(int x, int y, int w, int h, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, map,args);

		// override the default game object call to get sprites and load the same sprites for every switch
		getSprites(map);

		// default proximity (how close in pixels to switch) is set to 5
		this.proximity = Integer.parseInt((String) args.get("prox"));

		// location of the target
		this.targetX= Integer.parseInt((String) args.get("tx"))*tileSize;
		this.targetY= Integer.parseInt((String) args.get("ty"))*tileSize;

	}

	public void setTarget(ArrayList<GameObject> gameObjects){

		for(GameObject gObj : gameObjects) {
			if(gObj.getTileX()==targetX && gObj.getTileY()==targetY){	
				// give the switch the correesponding target object
				target = gObj;				
				// if the target has a timer, give the target object the corresponding switch
//				if(target instanceof Timed){
//					gObj.setSwitch(target);
//				}
				return;
			} 
		}
		target = null;
	}

	protected void getSprites(TiledMap map) throws SlickException{
		sprites.add(new Image("data/lever_left.png"));
		sprites.add(new Image("data/lever_right.png"));
	}


	public void draw(int mapX, int mapY, int mouseX, int mouseY){
		// unturned switch
		Image im = sprites.get(0);
		if(state){
			// turned switch
			im = sprites.get(1);
		}
		im.draw(rect.getX()-mapX,rect.getY()-mapY);		
	}

	public boolean canCollide(){
		return false;
	}

	public void update(int mouseX, int mouseY){};

	public void toggle(){
		state = !state;		
		target.toggle();
	}

	public void restoreTimedSwitch(){
		state = false;
	}


}
