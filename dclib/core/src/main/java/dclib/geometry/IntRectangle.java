package dclib.geometry;

import com.badlogic.gdx.math.Rectangle;


public final class IntRectangle {

	private final int x;
	private final int y;
	private final int width;
	private final int height;
	
	public IntRectangle(final int x, final int y, final int width, final int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public IntRectangle(final IntRectangle other) {
		this(other.x, other.y, other.width, other.height);
	}
	
	public final int x() {
		return x;
	}
	
	public final int y() {
		return y;
	}
	
	public final int width() {
		return width;
	}
	
	public final int height() {
		return height;
	}
	
	public final Rectangle toRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
}
