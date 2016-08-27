package dclib.epf.parts;

import org.apache.commons.lang3.ArrayUtils;

import dclib.physics.BodyType;

public final class PhysicsPart {

	private final BodyType bodyType;
	private final int[] collisionGroups;
	private float gravityScale = 1;

	public PhysicsPart(final BodyType bodyType, final int[] collisionGroups) {
		this.bodyType = bodyType;
		this.collisionGroups = collisionGroups;
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

	public final boolean containsAny(final int... collisionGroups) {
		for (int collisionGroup : collisionGroups) {
			if (ArrayUtils.contains(this.collisionGroups, collisionGroup)) {
				return true;
			}
		}
		return false;
	}

}
