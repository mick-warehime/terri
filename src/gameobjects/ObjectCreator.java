package gameobjects;

import org.newdawn.slick.SlickException;

public interface ObjectCreator {

	
	public boolean hasObject();	
	public Object getObject() throws SlickException;
	
}
