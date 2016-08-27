package dclib.system;

import java.util.ArrayList;
import java.util.List;

public final class Advancer {

	private final List<Updater> updaters = new ArrayList<Updater>();
	private float accumulatedDelta = 0;

	public final Advancer add(final Updater updater) {
		updaters.add(updater);
		return this;
	}

	public final void advance(final float delta) {
		accumulatedDelta += delta;
		final float maxUpdateDelta = 0.01f;
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
