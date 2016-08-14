package dclib.epf.parts;

import dclib.physics.BodyType;

public final class PhysicsPart {

	private final BodyType bodyType;

	public PhysicsPart(final BodyType bodyType) {
		this.bodyType = bodyType;
	}

	public final BodyType getBodyType() {
		return bodyType;
	}

}
