package dclib.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public final class ScreenHelper {

	private final float pixelsPerUnit;
	private final Camera camera;
	private final Rectangle viewport;

	public ScreenHelper(final float pixelsPerUnit, final Camera camera) {
		this(pixelsPerUnit, camera, new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
	}

	public ScreenHelper(final float pixelsPerUnit, final Camera camera, final Rectangle viewport) {
		this.pixelsPerUnit = pixelsPerUnit;
		this.camera = camera;
		this.viewport = viewport;
	}

	public final void setProjectionMatrix(final Batch batch) {
		Matrix4 matrix = getScaledProjectionMatrix(1);
		batch.setProjectionMatrix(matrix);
	}

	public final void setScaledProjectionMatrix(final Batch batch) {
		Matrix4 matrix = getScaledProjectionMatrix(pixelsPerUnit);
		batch.setProjectionMatrix(matrix);
	}

	public final void setScaledProjectionMatrix(final ShapeRenderer shapeRenderer) {
		Matrix4 matrix = getScaledProjectionMatrix(pixelsPerUnit);
		shapeRenderer.setProjectionMatrix(matrix);
	}

	public final float getPixelsPerUnit() {
		return pixelsPerUnit;
	}

	public final Vector2 toPixelUnits(final Vector2 worldUnits) {
		return worldUnits.cpy().scl(pixelsPerUnit);
	}

	public final Vector2 toWorldUnits(final float pixelX, final float pixelY) {
		return new Vector2(pixelX / pixelsPerUnit, pixelY / pixelsPerUnit);
	}

	public final Vector2 toWorldCoords(final float screenX, final float screenY) {
		Vector3 worldCoords3 = new Vector3(screenX, screenY, 0);
		camera.unproject(worldCoords3, viewport.x, viewport.y, viewport.width, viewport.height);
		worldCoords3.scl(1 / pixelsPerUnit);
		return new Vector2(worldCoords3.x, worldCoords3.y);
	}

	private Matrix4 getScaledProjectionMatrix(final float scale) {
		Matrix4 matrix = new Matrix4(camera.combined);
		return matrix.scale(scale, scale, 1);
	}

}
