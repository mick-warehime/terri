package actors;

import java.util.ArrayList;
import java.util.Properties;

import main.CollisionHandler;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.tiled.TiledMap;

import commands.DieCommand;
import gameobjects.Etherable;
import graphics.EtherEnemyGraphics;

public class EtherEnemy extends Enemy implements Etherable{

	protected Rectangle etherRect;
	protected boolean isEther = false;
	protected boolean isPut = false;
	protected boolean isTimed = false;
	protected boolean isMoving = true;
	protected int pixelHeight;
	protected int pixelWidth;
	private int putX;
	private int putY;
	

	private CollisionHandler collisionHandler;
	private int[] mousePos; 

	public EtherEnemy(int x, int y, int w, int h, String name, TiledMap map, Properties args ) throws SlickException {
		super(x,y,w,h,name,map,args);

		etherRect = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

		this.graphics = new EtherEnemyGraphics(status,etherRect,"data/enemy2.png");
		
	}

	public EtherEnemy(int x, int y) throws SlickException {
		super(x,y);	
		
		// dont know why this has to be here!!

		etherRect = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());

		graphics = new EtherEnemyGraphics(status,etherRect,"data/enemy2.png");
		
	}


	public void incorporateCollisionHandler(CollisionHandler collisionHandler){
		super.incorporateCollisionHandler(collisionHandler);
		this.collisionHandler = collisionHandler;
	}


	@Override
	public void setObjectToEther() {
		// TODO Auto-generated method stub
		isMoving = false;
		isEther = true;
		isPut = false;
		etherRect.setLocation(rect.getX(),rect.getY());	
		status.gainEffect("ethered",1000000000);
	}

	@Override
	public void put() {
		// TODO Auto-generated method stub
		if(canPut()){
			isPut = true;
			status.removeEffect("ethered");

			putX = (int) (mousePos[0]-rect.getWidth()/2);
			putY = (int) (mousePos[1]-rect.getHeight()/2);
			rect.setLocation(putX,putY);
		}
	}

	@Override
	public void restore() {
		status.removeEffect("ethered");
		// TODO Auto-generated method stub
		if(canRestore()){
			// TODO Auto-generated method stub
			isPut = false;
			isEther = false;
			rect.setLocation(etherRect.getX(),etherRect.getY());
		}
	}

	@Override
	public boolean canPut(){
		boolean checkTransparent = true;
		boolean answer = !collisionHandler.lineOfSightCollisionToPlayer(rect,checkTransparent);
		answer = answer && collisionHandler.canPlaceEtherAt(rect);
 		return answer;
	}

	@Override
	public boolean canRestore() {
		boolean answer = !collisionHandler.isCollidedWithObjects(rect);
		answer = answer && !collisionHandler.isCollidedWithPlayer(etherRect);
		return answer;
	}

	@Override
	public Rectangle getEtherRect() {
		// TODO Auto-generated method stub
		return etherRect;
	}

	public void update(){
		
		
		if(isEther && !isPut){
			//		eventually used to update doors/elevators etc;
			int hoverX = (int) (mousePos[0]-rect.getWidth()/2);
			int hoverY = (int) (mousePos[1]-rect.getHeight()/2);
			rect.setLocation(hoverX,hoverY);		
		}else{
			super.update(); //Normal enemy update
		}
	}


	public void render(int mapX, int mapY){
		((EtherEnemyGraphics)graphics).render(mapX, mapY, mousePos[0], mousePos[1], isEther, isPut, canPut());
	}

	@Override
	public void onCollisionDo(Class collidingObjectClass, Shape collidingObjectShape) {
		
		 
		if(canCollide()){
			super.onCollisionDo(collidingObjectClass, collidingObjectShape);
		}
	}

	@Override
	public ArrayList<Command> onCollisionBroadcast(Class collidingObjectClass, Shape collidingObjectShape) {
		
		if(canCollide()){
			return super.onCollisionBroadcast(collidingObjectClass, collidingObjectShape);
		}
		return new ArrayList<Command>();
	}
	
	
	public boolean canCollide(){
		return isPut || !isEther;
	}



	@Override
	public void setMousePosition(int[] mousePos) {
		this.mousePos = mousePos;
		
	}
}
