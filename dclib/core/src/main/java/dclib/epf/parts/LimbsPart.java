package dclib.epf.parts;

import java.util.List;

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
		boolean flip = flipX || flipY;
		float flipAxisAngle = Float.NaN;
		if (flipX) {
			flipAxisAngle = 90;
		}
		if (flipY) {
			flipAxisAngle = 0;
		}
		root.update(flip, flipAxisAngle);
	}

}
