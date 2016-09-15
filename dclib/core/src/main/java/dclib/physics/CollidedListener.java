package dclib.physics;

import dclib.epf.Entity;

public interface CollidedListener {

	void collided(final Entity collider, final Entity collidee);
	
}
