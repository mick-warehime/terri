package gameobjects;

import java.util.ArrayList;
import java.util.Properties;
import graphics.AnimatedGraphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class SwitchObject extends GameObject implements Interactive {
	// state  = false for left/not active and state = 1 for right/active
	protected boolean state = false;
	protected GameObject target;
	protected String targetName;
	protected AnimatedGraphics animatedDrawer;

	public SwitchObject(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);


		this.targetName = (String) args.get("target");
		
		ArrayList<String> fileNames = new ArrayList<String>();
	
		fileNames.add("data/lever_left.png");
		fileNames.add("data/lever_right.png");
	
		this.animatedDrawer = new AnimatedGraphics(rect,map,tileX,tileY,fileNames);
	}

	public void setTarget(ArrayList<GameObject> gameObjects){

		for(GameObject gObj : gameObjects) {

			if(gObj.getName().equals(targetName)){	
				// give the switch the correesponding target object
				target = gObj;				

				return;
			} 
		}
		target = null;
	}

	public boolean canCollide(){
		return false;
	}

	public void toggle(){

		state = !state;		
		target.toggle();
	}


	public void draw(int mapX, int mapY, int mouseX, int mouseY){
		
		int imageIndex = 0;
		if(state){
			imageIndex = 1;
		}
		animatedDrawer.render(mapX, mapY, imageIndex);
		
	}

 
	public void update(int mouseX, int mouseY){};
}
