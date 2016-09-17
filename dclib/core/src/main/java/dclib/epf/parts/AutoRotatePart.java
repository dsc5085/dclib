package dclib.epf.parts;

import com.badlogic.gdx.math.Vector2;

public final class AutoRotatePart {

	// TODO: move oldCenter to a new part
	private Vector2 oldCenter;

	public final Vector2 getOldCenter() {
		return oldCenter;
	}

	public final void setOldCenter(final Vector2 oldCenter) {
		this.oldCenter = oldCenter;
	}

}
