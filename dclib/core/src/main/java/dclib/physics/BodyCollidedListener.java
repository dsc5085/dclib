package dclib.physics;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;

public interface BodyCollidedListener {

	void collided(final Entity entity, final Vector2 offset);

}
