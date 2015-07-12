package dclib.util;

public final class IntTimer {

	private int maxTime = 0;
	private int time = 0;
	
	public IntTimer() {
	}
	
	public IntTimer(final int maxTime) {
		this.maxTime = maxTime;
	}
	
	public final int getMaxTime() {
		return maxTime;
	}
	
	public final void setMaxTime(final int maxTime) {
		this.maxTime = maxTime;
	}
	
	public final int getRemainingTime() {
		return maxTime - time;
	}
	
	public final boolean isElapsed() {
		return time >= maxTime;
	}
	
	public final int getElapsedPercent() {
		return time / maxTime;
	}
	
	public final void reset() {
		time = 0;
	}
	
	public final void tick(final int delta) {
		time = Math.min(time + delta, maxTime);
	}

}
