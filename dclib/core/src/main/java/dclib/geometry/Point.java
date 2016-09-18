package dclib.geometry;

public final class Point {

	private final int x;
	private final int y;
	
	public Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public final int x() {
		return x;
	}
	
	public final int y() {
		return y;
	}
	
}
