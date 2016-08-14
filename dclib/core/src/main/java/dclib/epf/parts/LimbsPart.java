package dclib.epf.parts;

import dclib.physics.Limb;

public final class LimbsPart {

	private final Limb root;

	public LimbsPart(final Limb root) {
		this.root = root;
	}

	public final void update() {
		root.update();
	}

}
