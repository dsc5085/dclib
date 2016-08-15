package dclib.limb;

public abstract class LimbAnimation {

	private boolean isPlaying = false;

	public final void play() {
		isPlaying = true;
	}

	public final void stop() {
		isPlaying = false;
	}

	public final void update(final float delta) {
		if (isPlaying) {
			updateAnimation(delta);
		}
	}

	protected abstract void updateAnimation(final float delta);

}
