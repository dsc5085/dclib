package dclib.physics;

import dclib.epf.Entity;

public interface CollisionFilter {

	boolean shouldFilter(final Entity e1, final Entity e2);

}
