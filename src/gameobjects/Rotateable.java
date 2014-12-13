package gameobjects;

public interface Rotateable {

	public void rotate(boolean rotateClockwise, int[] mousePos);
	public void restoreToOriginalAngle();
}
