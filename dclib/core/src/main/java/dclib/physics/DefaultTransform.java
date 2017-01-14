package dclib.physics;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import dclib.geometry.PolygonUtils;

public final class DefaultTransform extends Transform {

	private final Polygon polygon;
	private Vector2 velocity = new Vector2();

    public DefaultTransform() {
        this(new Polygon(PolygonUtils.INSTANCE.createDefault()), 0);
    }

	public DefaultTransform(final Polygon polygon, final float z) {
		super(z);
		this.polygon = polygon;
	}

	public DefaultTransform(final Transform transform) {
		super(transform.getZ());
		polygon = new Polygon(transform.getVertices());
		Vector2 origin = transform.getOrigin();
		polygon.setOrigin(origin.x, origin.y);
		Vector2 position = transform.getPosition();
		polygon.setPosition(position.x, position.y);
		polygon.setRotation(transform.getRotation());
		Vector2 scale = transform.getScale();
		polygon.setScale(scale.x, scale.y);
	}

	public final float[] getTransformedVertices() {
		return polygon.getTransformedVertices();
	}

	@Override
	public final float[] getVertices() {
		return polygon.getVertices();
	}

	@Override
	public final Vector2 getOrigin() {
		return new Vector2(polygon.getOriginX(), polygon.getOriginY());
	}

	@Override
	public final Vector2 getScale() {
		return new Vector2(polygon.getScaleX(), polygon.getScaleY());
	}

	@Override
	public final void setScale(final Vector2 scale) {
		polygon.setScale(scale.x, scale.y);
	}

	@Override
	public final Vector2 getLocalSize() {
		Rectangle bounds = PolygonUtils.INSTANCE.bounds(polygon.getVertices());
        return bounds.getSize(new Vector2());
    }

	@Override
	public final Vector2 getPosition() {
		return new Vector2(polygon.getX(), polygon.getY());
	}

	@Override
	public final void setPosition(final Vector2 position) {
		polygon.setPosition(position.x, position.y);
	}

	@Override
	public final Rectangle getBounds() {
		return new Rectangle(polygon.getBoundingRectangle());
	}

	@Override
	public final float getRotation() {
		return polygon.getRotation();
	}

	@Override
	public final void setRotation(final float degrees) {
		polygon.setRotation(degrees);
	}

	@Override
	public final Vector2 getVelocity() {
		return velocity.cpy();
	}

	@Override
	public final void setVelocity(final Vector2 velocity) {
		this.velocity = velocity;
	}

	@Override
	public final void applyImpulse(final Vector2 impulse) {
		velocity.add(impulse);
	}

}
