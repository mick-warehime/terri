package gameobjects;

import graphics.TimedEtherGraphics;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class TimedPlatform extends EtherObject  implements Timed,Rotateable{

	private int duration;
	private long putTime;
	private TimedEtherGraphics timedEtherGraphics;

	public TimedPlatform(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map,args);

		// default duration is set to 1000 milliseconds	
		this.duration = Integer.parseInt((String) args.get("duration"))*1000;
		this.timedEtherGraphics = new TimedEtherGraphics(shape,etherShape,map,x, y, w, h);

	}

	public void render(int mapX, int mapY){
		long timeElapsed = getTime()-putTime; 
		float percentTimeLeft = (float) Math.abs(timeElapsed-duration)/duration;		
		percentTimeLeft = (float) Math.max(percentTimeLeft, 0.2);
		
		timedEtherGraphics.render(mapX, mapY, mousePos[0],mousePos[1], isEther, isPut, canPut(), percentTimeLeft);
		
	}

	@Override
	public void update(){
		super.update();
		if(isPut){
			long timeElapsed = getTime()-putTime; 
			if(timeElapsed > duration){
				this.restore();
			}
		}
	}

	public void put(){
		super.put();

		putTime = getTime();
	}

	@Override
	public void rotate(boolean rotateClockwise) {
		String out = "Rotating";
		if (rotateClockwise){ out+= " clockwise";}
		else {out+= " counter-clockwise";}
		System.out.println(out);
		
	}


}
