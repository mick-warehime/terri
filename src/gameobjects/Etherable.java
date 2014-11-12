package gameobjects;

import org.newdawn.slick.geom.Rectangle;

public interface Etherable {

	public abstract void setObjectToEther();

	public abstract void put();

	public abstract void restore();

	public abstract boolean canPut();

	// check if anything is at the original position  (ether Rect) before restoring 
	public abstract boolean canRestore();

	public abstract Rectangle getEtherRect();

	public abstract Rectangle getRect();

	public abstract void setMousePosition(int[] mousePos);

}