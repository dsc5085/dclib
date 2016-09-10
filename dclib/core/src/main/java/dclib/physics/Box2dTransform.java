package dclib.physics;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import dclib.geometry.Transform;
import dclib.geometry.VertexUtils;

public final class Box2dTransform extends Transform {

	private final Body body;

	public Box2dTransform(final float z, final Body body) {
		super(z);
		this.body = body;
	}

	@Override
	public final Vector2 getOrigin() {
		return new Vector2();
	}

	@Override
	public final Vector2 getScale() {
		// TODO: need scale field
		return new Vector2(1, 1);
	}

	@Override
	public final void setScale(final Vector2 scale) {
		// TODO:
	}

	@Override
	public final Vector2 getSize() {
		return getBounds().getSize(new Vector2());
	}

	@Override
	public final Vector2 getPosition() {
		return body.getPosition().cpy();
	}

	@Override
	public void setPosition(final Vector2 position) {
		body.setTransform(position, body.getAngle());
	}

	@Override
	public final Rectangle getBounds() {
		Rectangle bounds = null;
		for (Fixture fixture : body.getFixtureList()) {
			Rectangle fixtureBounds;
			Shape shape = fixture.getShape();
			switch (shape.getType()) {
			case Polygon:
				PolygonShape polygonShape = (PolygonShape)shape;
				fixtureBounds = getBounds(polygonShape);
				break;
			case Circle:
				CircleShape circleShape = (CircleShape)shape;
				fixtureBounds = getBounds(circleShape);
				break;
			default:
				throw new UnsupportedOperationException("Cannot get size for shape type " + shape.getType());
			}
			bounds = bounds == null ? fixtureBounds : bounds.merge(fixtureBounds);
		}
		Vector2 newPosition = bounds.getPosition(new Vector2()).add(getPosition());
		return bounds.setPosition(newPosition);
	}

	@Override
	public final float getRotation() {
		return body.getAngle();
	}

	@Override
	public final void setRotation(final float degrees) {
		body.setTransform(body.getPosition(), degrees);
	}

	private Rectangle getBounds(final PolygonShape polygonShape) {
		float[] vertices = new float[polygonShape.getVertexCount() * 2];
		for (int i = 0; i < polygonShape.getVertexCount(); i++) {
			Vector2 vertex = new Vector2();
			polygonShape.getVertex(i, vertex);
			vertices[i * 2] = vertex.x;
			vertices[i * 2 + 1] = vertex.y;
		}
		return VertexUtils.bounds(vertices);
	}

	private Rectangle getBounds(final CircleShape circleShape) {
		float diameter = circleShape.getRadius() * 2;
		Vector2 position = circleShape.getPosition();
		return new Rectangle(position.x - diameter / 2, position.y - diameter / 2, diameter, diameter);
	}

}
