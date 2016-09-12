package dclib.geometry;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public final class DefaultTransform extends Transform {

	private final Polygon polygon;

	public DefaultTransform(final float z, final Polygon polygon) {
		super(z);
		this.polygon = polygon;
	}

	public final float[] getTransformedVertices() {
		return polygon.getTransformedVertices();
	}

	@Override
	public Vector2 getOrigin() {
		return new Vector2(polygon.getOriginX(), polygon.getOriginY());
	}

	@Override
	public Vector2 getScale() {
		return new Vector2(polygon.getScaleX(), polygon.getScaleY());
	}

	@Override
	public void setScale(final Vector2 scale) {
		polygon.setScale(scale.x, scale.y);
	}

	@Override
	public Vector2 getSize() {
		Rectangle bounds = VertexUtils.bounds(polygon.getVertices());
		return bounds.getSize(new Vector2());
	}

	@Override
	public Vector2 getPosition() {
		return new Vector2(polygon.getX(), polygon.getY());
	}

	@Override
	public void setPosition(final Vector2 position) {
		polygon.setPosition(position.x, position.y);
	}

	@Override
	public final Rectangle getBounds() {
		return new Rectangle(polygon.getBoundingRectangle());
	}

	@Override
	public float getRotation() {
		return polygon.getRotation();
	}

	@Override
	public void setRotation(final float degrees) {
		polygon.setRotation(degrees);
	}

}
