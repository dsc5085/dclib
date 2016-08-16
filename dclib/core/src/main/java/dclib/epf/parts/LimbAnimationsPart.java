package dclib.epf.parts;

import java.util.Map;

import dclib.limb.LimbAnimation;

public final class LimbAnimationsPart {

	private final Map<String, LimbAnimation> animations;

	public LimbAnimationsPart(final Map<String, LimbAnimation> animations) {
		this.animations = animations;
	}

	public final LimbAnimation get(final String key) {
		return animations.get(key);
	}

	public final void update(final float delta) {
		for (LimbAnimation animation : animations.values()) {
			animation.update(delta);
		}
	}

}
