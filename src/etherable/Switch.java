package etherable;

import java.util.Properties;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Switch extends SwitchObject{


	public Switch(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map, args);
		
		// how close you need to be to use the switch
		this.proximity = Integer.parseInt((String) args.get("prox"));
	}
}
