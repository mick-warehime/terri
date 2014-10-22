package etherable;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Switch extends GameObject{
	// state  = 1 for up and state = 2 for down
	private boolean state = false;
	private GameObject target;

	public Switch(int objX, int objY, int tarX, int tarY, TiledMap map, ArrayList<GameObject> gameObjects) throws SlickException {		
		super(objX, objY, map);

		getSprites();

		setTarget(tarX, tarY,  gameObjects);
	}

	private void setTarget(int targetX, int targetY, ArrayList<GameObject> gameObjects){

		int tX = targetX*this.tileSize;
		int tY = targetY*this.tileSize;
		for(GameObject gObj : gameObjects) {

			if(gObj.getTileX()==tX && gObj.getTileY()==tY){				
				target = gObj;
				return;
			} 
		}
		target = null;
	}

	protected void setObjectDimensions(){
		this.h = this.tileSize;
		this.w = this.tileSize;
	}

	private void getSprites() throws SlickException{
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
		System.out.println("IM TOOGLED");
	}


}
