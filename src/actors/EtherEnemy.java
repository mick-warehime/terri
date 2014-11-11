package actors;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import gameobjects.Etherable;
import gameobjects.InteractiveCollideable;
import graphics.EtherGraphics;

public class EtherEnemy extends Enemy implements InteractiveCollideable, Etherable{

	protected Rectangle etherRect;
	protected boolean isEther = false;
	protected boolean isPut = false;
	protected boolean isActive = false;
	protected boolean isTimed = false;
	protected EtherGraphics etherGraphics;
	
	public EtherEnemy(int x, int y, Properties args ) throws SlickException {
		super(x,y,args);
			 
		
		
	}
	
	
	@Override
	public void setObjectToEther() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void put(int mouseX, int mouseY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restore() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canPut() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRestore() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rectangle getEtherRect() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
}
