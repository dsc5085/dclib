package dclib.epf.systems;

import dclib.epf.Entity;
import dclib.epf.EntitySystem;
import dclib.epf.parts.LimbsPart;

public final class LimbsSystem extends EntitySystem {

	@Override
	public final void update(final float delta, final Entity entity) {
		if (entity.hasActive(LimbsPart.class)) {
			entity.get(LimbsPart.class).update();
		}
	}

}
