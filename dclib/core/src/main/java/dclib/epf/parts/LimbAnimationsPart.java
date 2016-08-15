package dclib.epf.parts;

import java.util.List;

import dclib.limb.LimbAnimation;

public final class LimbAnimationsPart {

	private final List<LimbAnimation> animations;

	public LimbAnimationsPart(final List<LimbAnimation> animations) {
		this.animations = animations;
	}

	public final LimbAnimation get(final int index) {
		return animations.get(index);
	}

	public final void update(final float delta) {
		for (LimbAnimation animation : animations) {
			animation.update(delta);
		}
	}

}
