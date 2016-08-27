package dclib.physics;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;

public interface CollidedListener {

	void collided(final Entity collider, final Entity collidee, final Vector2 offset);

}
