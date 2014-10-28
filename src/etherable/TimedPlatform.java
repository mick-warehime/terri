package etherable;

import org.newdawn.slick.tiled.TiledMap;

public class TimedPlatform extends EtherObject implements Timed {

	private int duration;
	private long putTime;

	public TimedPlatform(int gi, int oi, TiledMap map) {		
		super(gi, oi, map);

		// default duration is set to 1000 milliseconds	
		String strDuration =  map.getObjectProperty(gi, oi, "duration", "1" );
		this.duration = Integer.parseInt(strDuration)*1000;

	}



	@Override
	public void update(int mouseX, int mouseY){
		super.update(mouseX, mouseY);
		if(isPut){
			long timeElapsed = getTime()-putTime; 
			if(timeElapsed > duration){
				this.restore();
			}
		}
	}

	public void put(int x, int y){
		super.put(x, y);

		putTime = getTime();
	}

	public long getTime() {
		return System.currentTimeMillis() ;
	}


}
