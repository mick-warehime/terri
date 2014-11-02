package etherable;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import commands.DieCommand;

public class DeadlyObject extends EtherObject implements InteractiveCollideable{
		
		private Rectangle slightlyBiggerRect;
		private int proximity = 8; 
	
	public DeadlyObject(int x, int y, int w, int h, TiledMap map) throws SlickException {
		super(x, y, w, h, map);
		slightlyBiggerRect = new Rectangle(rect.getX()-proximity,rect.getY()-proximity,rect.getWidth()+2*proximity,rect.getHeight()+2*proximity);
		System.out.println(slightlyBiggerRect.getY()+" "+rect.getY());
	}
	
//	public void update(int mouseX, int mouseY){
//		super.update(mouseX,mouseY);
//		//Resolve collision with player
//		if (collisionHandler.isCollidedWithPlayer(slightlyBiggerRect)){			
//			collisionHandler.addToCommandStack(new DieCommand());
//		}
//	}

	@Override
	public void onCollisionDo() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public Command onCollisionBroadcast() {
		// TODO Auto-generated method stub
		return new DieCommand();
	}
	

	
}
