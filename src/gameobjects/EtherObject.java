package gameobjects;


import graphics.EtherGraphics;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

public class EtherObject extends GameObject implements Etherable {

	
	
	protected int putX;
	protected int putY;

	protected Shape etherShape;
	protected boolean isEther = false;
	protected boolean isPut = false;
	protected boolean isTimed = false;
	protected EtherGraphics etherGraphics;
	protected int[] mousePos;


	public EtherObject(int x, int y, int w, int h, String name, TiledMap map,Properties args) throws SlickException {
		super(x, y, w, h, name, map,args);
		
		// used for collision detection		
		etherShape = new Rectangle(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		
		this.etherGraphics = new EtherGraphics(shape,etherShape,map,x, y, w, h);
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
			putX = (int) (mousePos[0]-shape.getWidth()/2);
			putY = (int) (mousePos[1]-shape.getHeight()/2);
			shape.setLocation(putX,putY);
		}
	}

 
	@Override
	public void restore() {
		
		if(canRestore()){
			// TODO Auto-generated method stub
			isPut = false;
			isEther = false;
			shape.setLocation(etherShape.getX(),etherShape.getY());
		}
	}


	public void update(){
		if(isEther && !isPut){
			//		eventually used to update doors/elevators etc;
			int hoverX = (int) (mousePos[0]-shape.getWidth()/2);
			int hoverY =(int) (mousePos[1]-shape.getHeight()/2);
			shape.setLocation(hoverX,hoverY);			
		}		
	}

	public void render(int mapX, int mapY){
		
		etherGraphics.render(mapX, mapY, mousePos[0], mousePos[1], isEther, isPut, canPut());
		 
	}


	/* (non-Javadoc)
	 * @see gameobjects.Etherable#canPut()
	 */
	@Override
	public boolean canPut(){
		boolean checkTransparent = true;
		boolean answer = !collisionHandler.lineOfSightCollisionToPlayer(shape,checkTransparent);
		answer = answer && collisionHandler.canPlaceEtherAt(shape);
		return answer;
	}

	// check if anything is at the original position  (ether Rect) before restoring 
	/* (non-Javadoc)
	 * @see gameobjects.Etherable#canRestore()
	 */
	@Override
	public boolean canRestore(){
		boolean answer = !collisionHandler.isCollidedWithObjects(shape);
		answer = answer && !collisionHandler.isCollidedWithPlayer(etherShape);
		answer = answer && !collisionHandler.isCollidedWithActor(etherShape);
		return answer;
	}

	/* (non-Javadoc)
	 * @see gameobjects.Etherable#getEtherRect()
	 */
	@Override
	public Shape getEtherRect(){
		return etherShape;
	}




	public boolean canCollide(){
		return isPut || !isEther;
	}




	@Override
	public void setMousePosition(int[] mousePos) {
		this.mousePos = mousePos;
	}

}
