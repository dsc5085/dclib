package dclib.physics.limb;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dclib.geometry.DefaultTransform;
import dclib.geometry.PolygonFactory;
import dclib.geometry.Transform;

public final class Limb {

	private Transform transform;
	private final List<Joint> joints = new ArrayList<Joint>();

	public Limb() {
		Polygon polygon = new Polygon(PolygonFactory.createDefault());
		transform = new DefaultTransform(0, polygon);
	}

	public Limb(final Transform transform) {
		this.transform = transform;
	}

	public final Transform getTransform() {
		return transform;
	}

	public final void setTransform(final Transform transform) {
		this.transform = transform;
	}

	public final Limb remove(final Transform limbTransform) {
		if (transform == limbTransform) {
			return this;
		}
		for (Joint joint : joints) {
			Transform transform = joint.getLimb().getTransform();
			if (transform == limbTransform) {
				joints.remove(joint);
				return joint.getLimb();
			}
			Limb foundLimb = joint.getLimb().remove(limbTransform);
			if (foundLimb != null)
			{
				return foundLimb;
			}
		}
		return null;
	}

	public final List<Limb> getDescendants() {
		List<Limb> descendants = new ArrayList<Limb>();
		for (Joint joint : joints) {
			descendants.add(joint.getLimb());
			descendants.addAll(joint.getLimb().getDescendants());
		}
		return descendants;
	}

	public final Limb addJoint(final Limb limb, final float parentLocalX, final float parentLocalY,
			final float childLocalX, final float childLocalY, final float rotation) {
		return addJoint(new Joint(limb, new Vector2(parentLocalX, parentLocalY), new Vector2(childLocalX, childLocalY),
				rotation));
	}

	public final Limb addJoint(final Joint joint) {
		joints.add(joint);
		return this;
	}

	public final void update(final boolean flip, final float flipAxisAngle) {
		for (Joint joint : joints) {
			update(joint, flip, flipAxisAngle);
		}
	}

	private void update(final Joint joint, final boolean flip, final float flipAxisAngle) {
		Limb childLimb = joint.getLimb();
		float childRotation = transform.getRotation() + joint.getRotation();
		if (flip) {
			childRotation = flipAxisAngle * 2 - childRotation;
		}
		childLimb.transform.setRotation(childRotation);
		Vector2 parentJointGlobal = transform.toGlobal(joint.getParentLocal());
		childLimb.transform.setGlobal(joint.getChildLocal(), parentJointGlobal);
		childLimb.update(flip, childRotation);
	}

}
