package etherable;

import org.newdawn.slick.geom.Rectangle;


// I HAVE CODED etherObjects in one class and we probably dont need this interface...

interface Ether {	
	public void restore();
	
	void drawTiles(int I, int J, int mapX, int mapY, float opacity);
	
	public boolean isCollided(Rectangle antherRect);
	void put(int x, int y);
	void draw(int mapX, int mapY, int mouseX, int mouseY);
	public boolean contains(int x, int y);
	void setObjectToEther();

	public Rectangle getRect();

	void update(int mouseX, int mouseY);

	public boolean isPut();
	public boolean isActive();

}


