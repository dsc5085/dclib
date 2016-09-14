package dclib.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import dclib.geometry.Transform;
import dclib.geometry.VertexUtils;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

public final class Box2dTransform extends Transform {

	private final Body body;
	private final Vector2 scale = new Vector2(1, 1);

	public Box2dTransform(final float z, final Body body) {
		super(z);
		this.body = body;
		body.setUserData(this);
	}

	@Override
	public final Vector2 getOrigin() {
		return new Vector2();
	}

	@Override
	public final Vector2 getScale() {
		return scale.cpy();
	}

	@Override
	public final void setScale(final Vector2 scale) {
		this.scale.set(scale);
		for (Fixture fixture : body.getFixtureList()) {
			Shape shape = fixture.getShape();
			// TODO: Scale other shape types?
			if (shape.getType() == Shape.Type.Polygon) {
				PolygonShape polygonShape = (PolygonShape)shape;
				// This gets the cached vertices from when the shape was first created
				float[] vertices = Box2DUtils.vertices(polygonShape);
				float[] scaledVertices = VertexUtils.scale(vertices, scale);
				polygonShape.set(scaledVertices);
			}
		}
	}

	@Override
	public final Vector2 getSize() {
		return Box2DUtils.size(body).cpy();
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
		return new Rectangle(Box2DUtils.aabb(body));
	}

	@Override
	public final float getRotation() {
		return body.getAngle() * MathUtils.radiansToDegrees;
	}

	@Override
	public final void setRotation(final float degrees) {
		body.setTransform(body.getPosition(), degrees * MathUtils.degreesToRadians);
	}

}
