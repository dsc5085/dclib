package dclib.geometry;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public final class Centrum {

	private final Polygon polygon;
	private final Vector2 local;

	public Centrum(final Polygon polygon, final Vector2 local) {
		this.polygon = polygon;
		this.local = local;
	}

	public final Vector2 getPosition() {
		return PolygonUtils.toGlobal(local, polygon);
	}

	// TODO: Change to angle?
	public final float getRotation() {
		return polygon.getRotation();
	}

}
