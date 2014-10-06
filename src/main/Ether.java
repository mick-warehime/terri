package main;

import org.newdawn.slick.geom.Rectangle;


// I HAVE CODED etherObjects in one class and we probably dont need this interface...

interface Ether {	
	public void get();
	public void restore();
	
	void drawTiles(int I, int J, int mapX, int mapY, float opacity);
	
	public boolean isCollided(Rectangle antherRect);
	void put(int x, int y);
	void draw(int mapX, int mapY, int mouseX, int mouseY);
	public boolean contains(int x, int y);
	public boolean isActive();
}


