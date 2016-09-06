package dclib.epf.parts;

import com.badlogic.gdx.math.Vector2;

public final class AutoRotatePart {

	// TODO: move oldposition to a new part
	private Vector2 oldPosition;

	public final Vector2 getOldCenter() {
		return oldPosition;
	}

	public final void setOldCenter(final Vector2 oldPosition) {
		this.oldPosition = oldPosition;
	}

}
