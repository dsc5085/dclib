package dclib.physics.collision;

import dclib.physics.Contacter;

public interface CollidedListener {

	void collided(final Contacter collider, final Contacter collidee);
	
}
