package dclib.epf.parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class CollisionDamagePart {

	private final List<Enum<?>> collisionGroups;
	private final float damage;

	public CollisionDamagePart(final float damage, final Enum<?>... collisionGroups) {
		this.damage = damage;
		this.collisionGroups = Arrays.asList(collisionGroups);
	}

	public final float getDamage() {
		return damage;
	}

	public final List<Enum<?>> getCollisionGroups() {
		return new ArrayList<Enum<?>>(collisionGroups);
	}

}
