package dclib.epf.parts;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Polygon;

import dclib.limb.Limb;

public final class LimbsPart {

	private final Limb root;
	private final List<Polygon> collisionPolygons = new ArrayList<Polygon>();
	private boolean flipX = false;
	private boolean flipY = false;

	public LimbsPart(final Limb root, final Limb... collisionLimbs) {
		this.root = root;
		for (Limb collisionLimb : collisionLimbs) {
			collisionPolygons.add(collisionLimb.getPolygon());
		}
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

	public final Limb remove(final Polygon descendantPolygon) {
		// TODO: Needs to take into account child limbs
		collisionPolygons.remove(descendantPolygon);
		return root.remove(descendantPolygon);
	}

	public final void update() {
		// TODO: Need to be able to handle both flipX and flipY at the same time
		float flipAxisAngle = flipX ? 90 : 0;
		flipDescendantPolygons(flipX, flipY);
		root.update(flipX || flipY, flipAxisAngle);
	}

	private void flipDescendantPolygons(final boolean flipX, final boolean flipY) {
		for (Limb limb : root.getDescendants()) {
			Polygon polygon = limb.getPolygon();
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
