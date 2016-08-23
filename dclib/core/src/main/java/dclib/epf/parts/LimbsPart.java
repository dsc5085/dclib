package dclib.epf.parts;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Polygon;

import dclib.limb.Limb;

public final class LimbsPart {

	private final Limb root;
	private final List<Polygon> collisionPolygons;
	private boolean flipX = false;
	private boolean flipY = false;

	public LimbsPart(final Limb root, final List<Polygon> collisionPolygons) {
		this.root = root;
		this.collisionPolygons = collisionPolygons;
	}

	public final Limb getRoot() {
		return root;
	}

	public final void setFlipX(final boolean flipX) {
		this.flipX = flipX;
	}

	public final void setFlipY(final boolean flipY) {
		this.flipY = flipY;
	}

	public final List<Polygon> getCollisionPolygons() {
		return new ArrayList<Polygon>(collisionPolygons);
	}

	public final void update() {
		// TODO: Need to be able to handle both flipX and flipY at the same time
		float flipAxisAngle = flipX ? 90 : 0;
		flipDescendantPolygons(flipX, flipY);
		root.update(flipX || flipY, flipAxisAngle);
	}

	private void flipDescendantPolygons(final boolean flipX, final boolean flipY) {
		for (Polygon polygon : root.getDescendants()) {
			float scaleY = Math.abs(polygon.getScaleY());
			if (flipX) {
				scaleY *= -1;
			}
			float scaleX = Math.abs(polygon.getScaleX());
			if (flipY) {
				scaleX *= -1;
			}
			polygon.setScale(scaleX, scaleY);
		}
	}

}
