package dclib.epf.parts;

import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import dclib.geometry.RectangleUtils;
import dclib.limb.Limb;

public final class LimbsPart {

	private final Limb root;
	private final List<Limb> collisionLimbs;
	private boolean flipX = false;
	private boolean flipY = false;

	public LimbsPart(final Limb root, final List<Limb> collisionLimbs) {
		this.root = root;
		this.collisionLimbs = collisionLimbs;
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

	public final Rectangle getCollisionBounds() {
		Vector2 min = new Vector2(Float.MAX_VALUE, Float.MAX_VALUE);
		Vector2 max = new Vector2(-Float.MAX_VALUE, -Float.MAX_VALUE);
		for (Limb limb : collisionLimbs) {
			Rectangle limbBounds = limb.getBounds();
			min.x = Math.min(min.x, limbBounds.x);
			min.y = Math.min(min.y, limbBounds.y);
			max.x = Math.max(max.x, RectangleUtils.right(limbBounds));
			max.y = Math.max(max.y, RectangleUtils.top(limbBounds));
		}
		return new Rectangle(min.x, min.y, max.x - min.x, max.y - min.y);
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
