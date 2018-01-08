package dclib.epf.parts;

import com.badlogic.gdx.graphics.Color;
import dclib.util.Timer;

public final class ColorChangePart {

	private Color fromColor;
	private Color toColor;
	private Timer changeTimer;
	
	public ColorChangePart() {
	}
	
	public ColorChangePart(final float maxChangeTime, final Color fromColor, final Color toColor) {
		this.fromColor = fromColor;
		this.toColor = toColor;
		changeTimer = new Timer(maxChangeTime);
	}
	
	public final Color getFromColor() {
		return fromColor;
	}
	
	public final Color getToColor() {
		return toColor;
	}
	
	public final Timer getChangeTimer() {
		return changeTimer;
	}
	
}
