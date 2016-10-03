package dclib.gamelogic;

import java.util.List;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import dclib.epf.EntityManager;
import dclib.epf.parts.CollisionRemovePart;
import dclib.physics.Contacter;
import dclib.physics.collision.CollidedListener;

public final class RemoveCollidedListener implements CollidedListener {

	private final EntityManager entityManager;

	public RemoveCollidedListener(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public final void collided(final Contacter collider, final Contacter collidee) {
		CollisionRemovePart collisionRemovePart = collider.getEntity().tryGet(CollisionRemovePart.class);
		if (collisionRemovePart != null) {
			List<Enum<?>> collisionGroups = collisionRemovePart.getCollisionGroups();
			boolean isCollideeStatic = collidee.getBody().getType() == BodyType.StaticBody;
			if (isCollideeStatic || collidee.getEntity().isAny(collisionGroups)) {
				entityManager.remove(collider.getEntity());
			}
		}
	}

}
