package dclib.epf.parts;

import dclib.physics.BodyType;

public final class PhysicsPart {

	private final BodyType bodyType;
	private float gravityScale = 1;

	public PhysicsPart(final BodyType bodyType) {
		this.bodyType = bodyType;
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

}
