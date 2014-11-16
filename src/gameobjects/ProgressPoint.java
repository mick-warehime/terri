package gameobjects;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class ProgressPoint extends GameObject{

	private boolean isActive;
	private int px;
	private int py;
	private int index;

	public ProgressPoint(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {
		super(x, y, w, h, name, map, args);
		// TODO Auto-generated constructor stub


		this.px = Integer.parseInt((String) args.get("x"))*tileSize;
		this.py = Integer.parseInt((String) args.get("y"))*tileSize;

		this.index = Integer.parseInt((String) args.get("index"));

		// the first prgoress point is the starting/active progress point
		if(this.index==0){
			isActive = true;
		}
		else{
			isActive = false;
		}

	}
	@Override
	protected void setGraphics(Shape rect, TiledMap map, int x, int y, int w, int h) throws SlickException{
		return;
	}
	
	
	public void update(){
		if(!isActive){
			if(collisionHandler.isCollidedWithPlayer(rect)){
				isActive = true;
			}
		}
	}

	public boolean canCollide(){
		return false;
	}

	public boolean isActive(){
		return isActive;
	}

	public int getPX(){
		return px;
	}

	public int getPY(){
		return py;
	}

	public int getIndex(){
		return index;
	}
	
	@Override
	public void render(int mapX, int mapY, int mouseX, int mouseY){
		return;
		
	}

	public void render(int i, int j){
	}

}
