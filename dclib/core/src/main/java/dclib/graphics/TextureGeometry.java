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
		FloatArray points = getPixelPoints(textureRegion);
		ConvexHull hull = new ConvexHull();
		FloatArray hullPoints = hull.computePolygon(points, true);
		removeExtraPointsFromCompute(hullPoints);
		if (hullPoints.size < PolygonUtils.NUM_TRIANGLE_VERTICES * 2) {
			int width = textureRegion.getRegionWidth();
			int height = textureRegion.getRegionHeight();
			hullPoints = new FloatArray(PolygonFactory.createRectangleVertices(width, height));
		}
		return hullPoints.toArray();
	}

	private static FloatArray getPixelPoints(final TextureRegion textureRegion) {
		FloatArray points = new FloatArray();
		Pixmap pixmap = TextureUtils.toPixmap(textureRegion);
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = 0; y < pixmap.getHeight(); y++) {
				if (!TextureUtils.isAlpha(pixmap.getPixel(x, y))) {
					points.addAll(x, y);
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
