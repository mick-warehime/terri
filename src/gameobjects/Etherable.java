package gameobjects;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public interface Etherable {

	public abstract void setObjectToEther();

	public abstract void put();

	public abstract void restore();

	public abstract boolean canPut();

	// check if anything is at the original position  (ether Rect) before restoring 
	public abstract boolean canRestore();

	public abstract Shape getEtherRect();

	public abstract Shape getRect();

	public abstract void setMousePosition(int[] mousePos);

}