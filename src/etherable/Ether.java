package etherable;

import java.util.ArrayList;


import org.newdawn.slick.geom.Rectangle;


// I HAVE CODED etherObjects in one class and we probably dont need this interface...

public interface Ether {	
	
	public void restore();

	public void put(int x, int y);
	public void draw(int mapX, int mapY, int mouseX, int mouseY);
	public void setObjectToEther();


	
	public void update(int mouseX, int mouseY);
	public void movingUpdate(ArrayList<Rectangle> blocks, ArrayList<Ether> etherObjs);
	void drawTiles(int I, int J, int mapX, int mapY, float opacity);

	
	public boolean isPut();
	public boolean isActive();
	public boolean isMoving();
	public boolean isCollided(Rectangle antherRect);
	public boolean contains(int x, int y);


	public Rectangle getRect();

}


