package dclib.system;

import java.util.Arrays;
import java.util.List;

public final class Advancer {

    public static final float MAX_UPDATE_DELTA = 0.01f;

	private final List<Updater> updaters;
	private float accumulatedDelta = 0;
	
	public Advancer(final Updater...updaters) {
		this.updaters = Arrays.asList(updaters);
	}

	public final void advance(final float delta) {
        final float maxFrameDelta = 0.25f;
        accumulatedDelta += Math.min(delta, maxFrameDelta);
        while (accumulatedDelta >= MAX_UPDATE_DELTA) {
            update(MAX_UPDATE_DELTA);
            accumulatedDelta -= MAX_UPDATE_DELTA;
        }
	}

	private void update(final float delta) {
		for (Updater updater : updaters) {
			updater.update(delta);
		}
	}

}
