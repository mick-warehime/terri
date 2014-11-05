package etherable;

import java.util.ArrayList;
import java.util.Properties;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class SwitchObject extends GameObject implements Interactive {
	// state  = 1 for up and state = 2 for down
	protected boolean state = false;
	protected GameObject target;
	private String targetName;

	public SwitchObject(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);

		// override the default game object call to get sprites and load the same sprites for every switch
		getSprites(map);

		this.targetName = (String) args.get("target");

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
		// unturned switch
		Image im = sprites.get(0);
		if(state){
			// turned switch
			im = sprites.get(1);
		}
		im.draw(rect.getX()-mapX,rect.getY()-mapY);		
	}

	protected void getSprites(TiledMap map) throws SlickException{
		sprites.add(new Image("data/lever_left.png"));
		sprites.add(new Image("data/lever_right.png"));
	}
	public void update(int mouseX, int mouseY){};
}
