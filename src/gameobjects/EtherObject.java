package gameobjects;


import graphics.EtherGraphics;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class EtherObject extends GameObject implements Etherable {

	
	
	protected int putX;
	protected int putY;

	protected Rectangle etherRect;
	protected boolean isEther = false;
	protected boolean isPut = false;
	protected boolean isTimed = false;
	protected EtherGraphics etherGraphics;
	private int[] mousePos;


	public EtherObject(int x, int y, int w, int h, String name, TiledMap map,Properties args) throws SlickException {
		super(x, y, w, h, name, map,args);
		
		// used for collision detection		
		etherRect = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		
		this.etherGraphics = new EtherGraphics(rect,etherRect,map,x, y, w, h);
	}



	 
	@Override
	public void setObjectToEther(){
		isEther = true;
		isPut = false;
	}

 
	@Override
	public void put(){

		if(isEther && !isPut){
			this.isPut = true;
			putX = (int) (mousePos[0]-rect.getWidth()/2);
			putY = (int) (mousePos[1]-rect.getHeight()/2);
			rect.setLocation(putX,putY);
		}
	}

 
	@Override
	public void restore() {
		
		if(canRestore()){
			// TODO Auto-generated method stub
			isPut = false;
			isEther = false;
			rect.setLocation(etherRect.getX(),etherRect.getY());
		}
	}


	public void update(){
		if(isEther && !isPut){
			//		eventually used to update doors/elevators etc;
			int hoverX = (int) (mousePos[0]-rect.getWidth()/2);
			int hoverY =(int) (mousePos[1]-rect.getHeight()/2);
			rect.setLocation(hoverX,hoverY);			
		}		
	}

	public void render(int mapX, int mapY, int mouseX, int mouseY){
		
		etherGraphics.render(mapX, mapY, mouseX, mouseY, isEther, isPut, canPut());
		 
	}


	/* (non-Javadoc)
	 * @see gameobjects.Etherable#canPut()
	 */
	@Override
	public boolean canPut(){
		boolean answer = !collisionHandler.lineOfSightCollision(rect);
		answer = answer && collisionHandler.canPlaceEtherAt(rect);
		return answer;
	}

	// check if anything is at the original position  (ether Rect) before restoring 
	/* (non-Javadoc)
	 * @see gameobjects.Etherable#canRestore()
	 */
	@Override
	public boolean canRestore(){
		boolean answer = !collisionHandler.isCollidedWithObjects(rect);
		answer = answer && !collisionHandler.isCollidedWithPlayer(etherRect);
		answer = answer && !collisionHandler.isCollidedWithActor(etherRect);
		return answer;
	}

	/* (non-Javadoc)
	 * @see gameobjects.Etherable#getEtherRect()
	 */
	@Override
	public Rectangle getEtherRect(){
		return etherRect;
	}




	public boolean canCollide(){
		return isPut || !isEther;
	}




	@Override
	public void setMousePosition(int[] mousePos) {
		this.mousePos = mousePos;
	}

}
