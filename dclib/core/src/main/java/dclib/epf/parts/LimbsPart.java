package dclib.epf.parts;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import dclib.geometry.Transform;
import dclib.limb.Limb;

public final class LimbsPart {

	private final Limb root;
	private final List<Transform> collisionTransforms = new ArrayList<Transform>();
	private boolean flipX = false;
	private boolean flipY = false;

	public LimbsPart(final Limb root, final Limb... collisionLimbs) {
		this.root = root;
		for (Limb collisionLimb : collisionLimbs) {
			collisionTransforms.add(collisionLimb.getTransform());
		}
		update();
	}

	public final Limb getRoot() {
		return root;
	}

	public final boolean getFlipX() {
		return flipX;
	}

	public final void setFlipX(final boolean flipX) {
		this.flipX = flipX;
	}

	public final void setFlipY(final boolean flipY) {
		this.flipY = flipY;
	}

	// TODO: Deprecated.  Collision transforms are no longer stored in Limbs
	public final List<Transform> getCollisionTransforms() {
		return new ArrayList<Transform>(collisionTransforms);
	}

	public final Limb remove(final Transform transform) {
		collisionTransforms.remove(transform);
		return root.remove(transform);
	}

	public final void update() {
		// TODO: Need to be able to handle both flipX and flipY at the same time
		float flipAxisAngle = flipX ? 90 : 0;
		flipDescendants(flipX, flipY);
		root.update(flipX || flipY, flipAxisAngle);
	}

	private void flipDescendants(final boolean flipX, final boolean flipY) {
		for (Limb limb : root.getDescendants()) {
			Transform transform = limb.getTransform();
			Vector2 scale = transform.getScale();
			scale.x = Math.abs(scale.x) * (flipY ? -1 : 1);
			scale.y = Math.abs(scale.y) * (flipX ? -1 : 1);
			transform.setScale(scale);
		}
	}

}
