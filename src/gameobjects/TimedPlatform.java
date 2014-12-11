package gameobjects;


import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.tiled.TiledMap;

public class TimedPlatform extends EtherObject  implements Timed,Rotateable{

	private int duration;
	private long putTime;
//	private TimedEtherGraphics timedEtherGraphics;

	public TimedPlatform(int x, int y, int w, int h, String name, TiledMap map, Properties args) throws SlickException {		
		super(x, y, w, h, name, map,args);

		// default duration is set to 1000 milliseconds	
		this.duration = Integer.parseInt((String) args.get("duration"))*1000;
//		this.timedEtherGraphics = new TimedEtherGraphics(shape,etherShape,map,x, y, w, h);

	}

	public void render(int mapX, int mapY){
		long timeElapsed = getTime()-putTime; 
		float percentTimeLeft = (float) Math.abs(timeElapsed-duration)/duration;		
		percentTimeLeft = (float) Math.max(percentTimeLeft, 0.2);

		etherGraphics.render(mapX, mapY, mousePos[0],mousePos[1], isEther, isPut, canPut(), percentTimeLeft);

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
	public void rotate(boolean rotateClockwise, int[] mousePos) {
		
		//I think the issue is with shape.setLocation
		// There is a discrepancy between minX and x for the shape object,
		// it seems.
	
		float rotationAngle;
		if (rotateClockwise){ 		
			rotationAngle = (float) (-0.5*Math.PI);

		}
		else {
			
			rotationAngle = (float) (0.5*Math.PI);
		}
		
		Transform rotation = Transform.createRotateTransform(rotationAngle,shape.getCenterX(),shape.getCenterY());
		Transform etherRotation = Transform.createRotateTransform(rotationAngle,etherShape.getCenterX(),etherShape.getCenterY());
		
		this.shape = shape.transform(rotation);
		this.etherShape = etherShape.transform(etherRotation);

		
		

	}


}
