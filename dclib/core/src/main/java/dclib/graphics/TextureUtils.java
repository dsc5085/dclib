package dclib.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.ConvexHull;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ScreenUtils;
import dclib.geometry.PolygonUtils;

public class TextureUtils {

	private static final EarClippingTriangulator triangulator = new EarClippingTriangulator();

	public static final Pixmap toPixmap(final TextureRegion textureRegion) {
		int width = textureRegion.getRegionWidth();
		int height = textureRegion.getRegionHeight();
		return toPixmap(textureRegion, width, height);
	}

	public static final Pixmap toPixmap(final TextureRegion textureRegion, final int width, final int height) {
		Matrix4 projection = new Matrix4();
		projection.setToOrtho2D(0, -height, width, height).scale(1, -1, 1);
		// TODO: Create spriteBatch just once per game
		SpriteBatch spriteBatch = new SpriteBatch();
		spriteBatch.setProjectionMatrix(projection);

		FrameBuffer frameBuffer = new FrameBuffer(Format.RGBA8888, width, height, false);
		frameBuffer.begin();
		spriteBatch.begin();
		spriteBatch.draw(textureRegion, 0, 0, width, height);
		spriteBatch.end();
		Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, width, height);
		frameBuffer.end();

		frameBuffer.dispose();
		spriteBatch.dispose();
		return pixmap;
	}

	public static boolean isAlpha(final int pixel) {
		return pixel == Color.CLEAR.toIntBits();
	}

	public static final float[] createConvexHull(final TextureRegion textureRegion) {
		float[] hullVertices;
		FloatArray points = getPixelPoints(textureRegion);
		FloatArray hullPoints = new ConvexHull().computePolygon(points, true);
		removeExtraPointsFromCompute(hullPoints);
        if (hullPoints.size >= PolygonUtils.INSTANCE.getNUM_TRIANGLE_VERTICES() * 2) {
            hullVertices = hullPoints.toArray();
        } else {
			int width = textureRegion.getRegionWidth();
			int height = textureRegion.getRegionHeight();
            hullVertices = PolygonUtils.INSTANCE.createRectangleVertices(width, height);
        }
        return hullVertices;
	}

	public static final PolygonRegion createPolygonRegion(final TextureRegion textureRegion) {
        float[] vertices = PolygonUtils.INSTANCE.createRectangleVertices(textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight());
        return createPolygonRegion(textureRegion, vertices);
	}

	public static final PolygonRegion createPolygonRegion(final TextureRegion textureRegion, final float[] vertices) {
        Rectangle bounds = PolygonUtils.INSTANCE.bounds(vertices);
        float[] shiftedVertices = PolygonUtils.INSTANCE.shift(vertices, -bounds.x, -bounds.y);
        // libgdx y-axis is flipped
		short[] triangles = triangulator.computeTriangles(shiftedVertices).toArray();
		return new PolygonRegion(textureRegion, shiftedVertices, triangles);
	}

	private static FloatArray getPixelPoints(final TextureRegion textureRegion) {
	    // The actual pixmap size is reduced by 1 pixel lengthwise and widthwise,
        // so need to add size to account for missing pixels
        int missingPixelsSize = 1;
		FloatArray points = new FloatArray();
		Pixmap pixmap = toPixmap(textureRegion, textureRegion.getRegionWidth() + missingPixelsSize,
                textureRegion.getRegionHeight() + missingPixelsSize);
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = 0; y < pixmap.getHeight(); y++) {
				if (!isAlpha(pixmap.getPixel(x, y))) {
					// libgdx y-axis is flipped
					points.addAll(x, pixmap.getHeight() - y - 1);
				}
			}
		}
		pixmap.dispose();
		return points;
	}

	private static void removeExtraPointsFromCompute(final FloatArray hullPoints) {
		int numExtraPoints = Math.min(hullPoints.size, 2);
		for (int i = 0; i < numExtraPoints; i++) {
			hullPoints.removeIndex(hullPoints.size - 1);
		}
	}
}
