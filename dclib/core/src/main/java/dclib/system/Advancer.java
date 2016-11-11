package dclib.system;

import java.util.Arrays;
import java.util.List;

public final class Advancer {

	private final List<Updater> updaters;
	private float accumulatedDelta = 0;
	
	public Advancer(final Updater...updaters) {
		this.updaters = Arrays.asList(updaters);
	}

	public final void advance(final float delta) {
        final float maxFrameDelta = 0.25f;
        final float maxUpdateDelta = 0.01f;
        accumulatedDelta += Math.min(delta, maxFrameDelta);
        while (accumulatedDelta >= maxUpdateDelta) {
			update(maxUpdateDelta);
			accumulatedDelta -= maxUpdateDelta;
		}
	}

	private void update(final float delta) {
		for (Updater updater : updaters) {
			updater.update(delta);
		}
	}

}
