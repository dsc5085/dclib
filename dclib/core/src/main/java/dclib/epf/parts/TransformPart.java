package dclib.epf.parts;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import dclib.geometry.PolygonFactory;
import dclib.geometry.PolygonUtils;
import dclib.geometry.VectorUtils;

public final class TransformPart {

	private Polygon polygon = new Polygon();
	private final float z;

	/**
	 * size stored for performance, rather than calculated on the fly when calling {@link #getSize()}
	 */
	private final Vector2 size;

	public TransformPart(final Polygon polygon, final float z) {
		this.polygon = polygon;
		this.z = z;
		size = PolygonUtils.size(polygon);
	}

	public final Polygon getPolygon() {
		return PolygonFactory.copy(polygon);
	}

	public final float[] getTransformedVertices() {
		return polygon.getTransformedVertices();
	}

	public final Vector2 getSize() {
		return size;
	}

	public final Vector2 getBoundingSize() {
		return polygon.getBoundingRectangle().getSize(new Vector2());
	}

	public final Vector2 getPosition() {
		return new Vector2(polygon.getX(), polygon.getY());
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

	public final void setCenter(final Vector2 center) {
		Vector2 offset = VectorUtils.offset(getCenter(), center);
		translate(offset);
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
		setCenter(oldCenter);
	}

	public final Rectangle getBoundingBox() {
		return new Rectangle(polygon.getBoundingRectangle());
	}

	public final void translate(final Vector2 offset) {
		setPosition(getPosition().add(offset));
	}

}
