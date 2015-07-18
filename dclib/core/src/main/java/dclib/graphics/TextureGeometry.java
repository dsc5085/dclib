package dclib.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.ConvexHull;
import com.badlogic.gdx.utils.FloatArray;

public final class TextureGeometry {

	private TextureGeometry() {
	}
	
	public static final float[] createConvexHull(final TextureRegion textureRegion) {
		FloatArray points = new FloatArray();
		Pixmap pixmap = TextureUtils.toPixmap(textureRegion);
		for (int x = 0; x < pixmap.getWidth(); x++) {
			for (int y = 0; y < pixmap.getHeight(); y++) {
				if (!TextureUtils.isAlpha(pixmap.getPixel(x, y))) {
					points.addAll(x, y);
				}
			}
		}
		ConvexHull hull = new ConvexHull();
		FloatArray hullPoints = hull.computePolygon(points, true);
		removeExtraPointsFromCompute(hullPoints);
		addPointsToEmptyHull(hullPoints);
		return hullPoints.toArray();
	}
	
	private static void removeExtraPointsFromCompute(final FloatArray hullPoints) {
		hullPoints.removeRange(hullPoints.size - 2, hullPoints.size - 1);
	}
	
	private static void addPointsToEmptyHull(final FloatArray hullPoints) {
		if (hullPoints.size <= 0) {
			hullPoints.addAll(0, 0, 1, 0, 1, 1, 0, 1);
		}
	}
	
}
