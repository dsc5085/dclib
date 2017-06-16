package dclib.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public final class ScreenHelper {

	private final float pixelsPerUnit;
	private final Viewport viewport;

	public ScreenHelper(final float pixelsPerUnit, final Viewport viewport) {
		this.pixelsPerUnit = pixelsPerUnit;
		this.viewport = viewport;
	}

	public final float getPixelsPerUnit() {
		return pixelsPerUnit;
	}

	// TODO: Don't expose this.
	public final Viewport getViewport() {
		return viewport;
	}

	public final void setProjectionMatrix(final Batch batch) {
		Matrix4 matrix = getScaledProjectionMatrix(1);
		batch.setProjectionMatrix(matrix);
	}

	public final void setScaledProjectionMatrix(final Batch batch) {
		batch.setProjectionMatrix(getScaledProjectionMatrix());
	}

	public final void setScaledProjectionMatrix(final ShapeRenderer shapeRenderer) {
		shapeRenderer.setProjectionMatrix(getScaledProjectionMatrix());
	}

	public final Vector2 toPixelUnits(final Vector2 worldUnits) {
		return worldUnits.cpy().scl(pixelsPerUnit);
	}

	public final Vector2 toWorldUnits(final float pixelX, final float pixelY) {
		return new Vector2(pixelX / pixelsPerUnit, pixelY / pixelsPerUnit);
	}

	public final Vector2 toWorldCoords(final Vector2 screenCoords) {
		Vector2 worldCoords = screenCoords.cpy();
		viewport.unproject(worldCoords);
		return worldCoords.scl(1 / pixelsPerUnit);
	}

	public final Matrix4 getScaledProjectionMatrix() {
		return getScaledProjectionMatrix(pixelsPerUnit);
	}

	private Matrix4 getScaledProjectionMatrix(final float scale) {
		Matrix4 matrix = new Matrix4(viewport.getCamera().combined);
		return matrix.scale(scale, scale, 1);
	}

}
