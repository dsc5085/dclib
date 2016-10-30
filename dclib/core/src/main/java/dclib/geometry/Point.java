package dclib.geometry;

import com.google.common.base.Objects;

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

	@Override
	public final boolean equals(final Object obj) {
		if (obj instanceof Point) {
			Point other = (Point)obj;
			return x == other.x && y == other.y;
		}
		return false;
	}

	@Override
	public final int hashCode() {
		return Objects.hashCode(x, y);
	}

}
