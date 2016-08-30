package dclib.epf.parts;

import java.util.Arrays;
import java.util.List;

import dclib.physics.BodyType;
import dclib.util.CollectionUtils;

public final class PhysicsPart {

	private final BodyType bodyType;
	// TODO: Move to a collisionpart class
	private final List<Enum<?>> collisionGroups;
	private float gravityScale = 1;

	public PhysicsPart(final BodyType bodyType, final Enum<?>... collisionGroups) {
		this.bodyType = bodyType;
		this.collisionGroups = Arrays.asList(collisionGroups);
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

	public final boolean containsAny(final List<Enum<?>> collisionGroups) {
		return CollectionUtils.containsAny(this.collisionGroups, collisionGroups);
	}

}
