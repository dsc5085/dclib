package dclib.system;

public abstract class Advancer {
	
	private float accumulatedDelta = 0;

	public final void advance(final float delta) {
		accumulatedDelta += delta;
		final float maxUpdateDelta = 0.01f;
		while (accumulatedDelta >= maxUpdateDelta) {
			update(maxUpdateDelta);
			accumulatedDelta -= maxUpdateDelta;
		}
	}
	
	protected abstract void update(final float delta);
	
}
