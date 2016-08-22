package dclib.epf.parts;

import org.apache.commons.lang3.ArrayUtils;

import dclib.physics.BodyType;

public final class PhysicsPart {

	private final BodyType bodyType;
	private final int[] collisionGroups;
	private float gravityScale = 1;

	public <E extends Enum<E>> PhysicsPart(final BodyType bodyType, final E[] collisionGroups) {
		this.bodyType = bodyType;
		this.collisionGroups = new int[collisionGroups.length];
		for (int i = 0; i < collisionGroups.length; i++) {
			this.collisionGroups[i] = collisionGroups[i].ordinal();
		}
	}

	public final BodyType getBodyType() {
		return bodyType;
	}

	public final float getGravityScale() {
		return gravityScale;
	}

	public final void setGravityScale(final float gravityScale) {
		this.gravityScale = gravityScale;
	}

	public final <E extends Enum<E>> boolean inCollisionGroups(final E collisionGroup) {
		return ArrayUtils.contains(collisionGroups, collisionGroup.ordinal());
	}

}
