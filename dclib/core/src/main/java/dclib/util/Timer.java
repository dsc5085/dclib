package dclib.util;

public final class Timer {

	private float maxTime;
	private float time;

	public Timer(final float maxTime) {
		this(maxTime, 0);
	}

	public Timer(final float maxTime, final float time) {
		this.maxTime = maxTime;
		this.time = time;
	}

	public final float getMaxTime() {
		return maxTime;
	}

	public final void setMaxTime(final float maxTime) {
		this.maxTime = maxTime;
	}

	public final float getRemainingTime() {
		return maxTime - time;
	}

	public final boolean isElapsed() {
		return time >= maxTime;
	}

	public final float getElapsedPercent() {
		return time / maxTime;
	}

	public final void reset() {
		time = 0;
	}

	public final boolean check() {
		boolean isElapsed = isElapsed();
		if (isElapsed) {
			reset();
		}
		return isElapsed;
	}

	public final void tick(final float delta) {
		time = Math.min(time + delta, maxTime);
	}

}
