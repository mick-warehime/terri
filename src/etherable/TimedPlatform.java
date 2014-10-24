package etherable;

import org.newdawn.slick.tiled.TiledMap;

public class TimedPlatform extends EtherObject implements Timed {

	private int duration;
	private long putTime;
	public TimedPlatform(int i, int j, TiledMap map, int duration) {
		super(i, j, map);

		// duration in milliseconds
		this.duration = duration*1000;

	}

	protected void setObjectDimensions(){
		this.h = this.tileSize;
		this.w = 7*this.tileSize;
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
