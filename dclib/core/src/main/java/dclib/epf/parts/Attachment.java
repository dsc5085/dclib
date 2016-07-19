package dclib.epf.parts;

import com.badlogic.gdx.math.Vector2;

public final class Attachment<T> {

	private final T object;
	private final Vector2 localPosition;
	
	public Attachment(final T object, final Vector2 localPosition) {
		this.object = object;
		this.localPosition = localPosition;
	}
	
	public final T getObject() {
		return object;
	}
	
	public final Vector2 getLocalPosition() {
		return localPosition.cpy();
	}
	
}
