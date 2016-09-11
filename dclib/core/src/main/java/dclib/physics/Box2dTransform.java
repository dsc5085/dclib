package dclib.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import dclib.geometry.Transform;
import net.dermetfan.gdx.physics.box2d.Box2DUtils;

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
		return body.getPosition();
	}

	@Override
	public void setPosition(final Vector2 position) {
		body.setTransform(position, body.getAngle());
	}

	@Override
	public final Rectangle getBounds() {
		return Box2DUtils.aabb(body);
	}

	@Override
	public final float getRotation() {
		return body.getAngle() * MathUtils.radiansToDegrees;
	}

	@Override
	public final void setRotation(final float degrees) {
		body.setTransform(body.getPosition(), degrees);
	}

}
