package dclib.util;

public final class FloatTimer {

	private float maxTime = 0;
	private float time = 0;
	
	public FloatTimer() {
	}
	
	public FloatTimer(final float maxTime) {
		this.maxTime = maxTime;
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
	
	public final void tick(final float delta) {
		time = Math.min(time + delta, maxTime);
	}
	
}
