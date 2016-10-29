package dclib.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public final class RenderUtils {

	private RenderUtils() {
	}

	public static final void clear() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

}
