package dclib.epf.parts;


public final class CollisionDamagePart {

	private final int[] collisionTypes;
	private final float damage;

	public CollisionDamagePart(final int[] collisionTypes, final float damage) {
		this.collisionTypes = collisionTypes;
		this.damage = damage;
	}

	public final int[] getCollisionTypes() {
		return collisionTypes;
	}

	public final float getDamage() {
		return damage;
	}

}
