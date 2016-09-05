package dclib.epf.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import dclib.geometry.PolygonUtils;
import dclib.geometry.VectorUtils;

public final class TransformPart {

	private Polygon polygon = new Polygon();
	private final float z;

	public TransformPart(final Polygon polygon, final float z) {
		this.polygon = polygon;
		this.z = z;
	}

	public final Polygon getPolygon() {
		return polygon;
	}

	public final float[] getTransformedVertices() {
		return polygon.getTransformedVertices();
	}

	public final Vector2 getScale() {
		return new Vector2(polygon.getScaleX(), polygon.getScaleY());
	}

	public final Vector2 getSize() {
		return PolygonUtils.size(polygon);
	}

	public final Vector2 getPosition() {
		return new Vector2(polygon.getX(), polygon.getY());
	}

	public final Vector3 getPosition3() {
		return new Vector3(polygon.getX(), polygon.getY(), z);
	}

	public final void setPosition(final Vector2 position) {
		polygon.setPosition(position.x, position.y);
	}

	public final float getZ() {
		return z;
	}

	public final Vector2 getCenter() {
		return PolygonUtils.center(polygon);
	}

	public final Vector2 getOrigin() {
		return new Vector2(polygon.getOriginX(), polygon.getOriginY());
	}

	public final void setOrigin(final Vector2 origin) {
		polygon.setOrigin(origin.x, origin.y);
	}

	public final float getRotation() {
		return polygon.getRotation();
	}

	public final void setRotation(final float degrees) {
		polygon.setRotation(degrees);
	}

	public final void setCenteredRotation(final float degrees) {
		Vector2 oldCenter = getCenter();
		setRotation(degrees);
		Vector2 offset = VectorUtils.offset(getCenter(), oldCenter);
		translate(offset);
	}

	public final Rectangle getBounds() {
		return new Rectangle(polygon.getBoundingRectangle());
	}

	public final void translate(final Vector2 offset) {
		polygon.translate(offset.x, offset.y);
	}

}
