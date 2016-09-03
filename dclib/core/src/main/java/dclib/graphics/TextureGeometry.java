package dclib.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.ConvexHull;
import com.badlogic.gdx.utils.FloatArray;

import dclib.geometry.PolygonFactory;
import dclib.geometry.PolygonUtils;

public final class TextureGeometry {

	private TextureGeometry() {
	}

	public static final float[] createConvexHull(final TextureRegion textureRegion) {
		float[] hullVertices;
		FloatArray points = getPixelPoints(textureRegion);
		FloatArray hullPoints = new ConvexHull().computePolygon(points, true);
		removeExtraPointsFromCompute(hullPoints);
		if (hullPoints.size >= PolygonUtils.NUM_TRIANGLE_VERTICES * 2) {
			hullVertices = hullPoints.toArray();
		} else {
			int width = textureRegion.getRegionWidth();
			int height = textureRegion.getRegionHeight();
			hullVertices = PolygonFactory.createRectangleVertices(width, height);
		}
		return hullVertices;
	}

	private static FloatArray getPixelPoints(final TextureRegion textureRegion) {
		FloatArray points = new FloatArray();
		Pixmap pixmap = TextureUtils.toPixmap(textureRegion);
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = 0; y < pixmap.getHeight(); y++) {
				if (!TextureUtils.isAlpha(pixmap.getPixel(x, y))) {
					// libgdx y-axis is flipped
					points.addAll(x, pixmap.getHeight() - y);
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
