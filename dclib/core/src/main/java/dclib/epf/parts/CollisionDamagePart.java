package dclib.epf.parts;


public final class CollisionDamagePart {

	private final Enum<?>[] collisionGroups;
	private final float damage;

	public CollisionDamagePart(final float damage, final Enum<?>... collisionGroups) {
		this.damage = damage;
		this.collisionGroups = collisionGroups;
	}

	public final float getDamage() {
		return damage;
	}

	public final Enum<?>[] getCollisionGroups() {
		return collisionGroups.clone();
	}

}
