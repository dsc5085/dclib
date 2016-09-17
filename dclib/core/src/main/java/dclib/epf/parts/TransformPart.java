package dclib.epf.parts;

import dclib.physics.Transform;

public final class TransformPart {

	private final Transform transform;

	public TransformPart(final Transform transform) {
		this.transform = transform;
	}

	public final Transform getTransform() {
		return transform;
	}

}
