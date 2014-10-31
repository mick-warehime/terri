package etherable;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TimedSwitch extends GameObject implements Interactive{
	// state  = 1 for up and state = 2 for down
	private boolean state = false;
	private GameObject target;
	private int targetX;
	private int targetY;
	private int duration;
	private long toggleTime = 0;


	public TimedSwitch(int x, int y, int w, int h, int tx, int ty, int prox, int duration, TiledMap map) throws SlickException {		
		super(x, y, w, h, map);

		// override the default game object call to get sprites and load the same sprites for every switch
		getSprites(map);

		// default proximity (how close in pixels to switch) is set to 5
		this.proximity = prox;
		
		// how long the switch stays on in milliseconds
		this.duration = duration*1000;
		
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

	public void update(int mouseX, int mouseY){
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
