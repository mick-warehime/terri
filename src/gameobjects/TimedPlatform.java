package gameobjects;

import graphics.EtherGraphics;

import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class TimedPlatform extends EtherObject  implements Timed,Rotateable{

	private int duration;
	private long putTime;

	public TimedPlatform(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map,args);

		// default duration is set to 1000 milliseconds	
		this.duration = Integer.parseInt((String) args.get("duration"))*1000;

	}

	public void render(int mapX, int mapY){
		long timeElapsed = getTime()-putTime; 
		float percentTimeLeft = (float) Math.abs(timeElapsed-duration)/duration;		
		percentTimeLeft = (float) Math.max(percentTimeLeft, 0.2);

		((EtherGraphics)graphics).render(mapX, mapY, mousePos[0],mousePos[1], isEther, isPut, canPut(), percentTimeLeft);

	}

	@Override
	public void update(){
		super.update();
		if(isPut){
			long timeElapsed = getTime()-putTime; 
			if(timeElapsed > duration){
				if (this.canRestore()){
					this.restore();
				}
			}
		}
	}

	
	
	public void put(){
		super.put();

		putTime = getTime();
	}

	public void restore() {
		super.restore();
		this.restoreToOriginalAngle();
		
	}
	
	
	
	@Override
	public void rotate(boolean rotateClockwise, int[] mousePos) {
	
		float rotationAngle;
		if (rotateClockwise){ 		
			rotationAngle = (float) (-0.5*Math.PI);		
		}
		else {
			rotationAngle = (float) (0.5*Math.PI);
		}
		// Do it the simple way, for rects only.
		float width = shape.getWidth();
		float height = shape.getHeight();
		
		
		shape = new Rectangle(
				shape.getCenterX() - height/2, shape.getCenterY()-width/2, height,width);
		
		graphics.setRect(shape);			
		graphics.rotateImages(rotationAngle);
		

	}

	@Override
	public void restoreToOriginalAngle() {
		((EtherGraphics)graphics).restoreOriginalAngle();
		
	}


}
