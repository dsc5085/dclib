package dclib.physics.limb;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.parts.TransformPart;
import dclib.physics.DefaultTransform;
import dclib.physics.Transform;

// TODO: Make generic, e.g. entity should be of a generic type
public final class Limb {

	private final Entity entity;
	private final List<Joint> joints = new ArrayList<Joint>();

	public Limb() {
		entity = new Entity();
		Transform transform = new DefaultTransform();
		entity.attach(new TransformPart(transform));
	}

	public Limb(final Entity entity) {
		this.entity = entity;
	}

	public final Entity getEntity() {
		return entity;
	}

	public final Transform getTransform() {
		return entity.tryGet(TransformPart.class).getTransform();
	}

	public final Limb remove(final Entity limbEntity) {
		if (entity == limbEntity) {
			return this;
		}
		for (Joint joint : joints) {
			Entity entity = joint.getLimb().entity;
			if (entity == limbEntity) {
				joints.remove(joint);
				return joint.getLimb();
			}
			Limb foundLimb = joint.getLimb().remove(limbEntity);
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
		Vector2 parentLocal = new Vector2(parentLocalX, parentLocalY);
		Vector2 childLocal = new Vector2(childLocalX, childLocalY);
		return addJoint(new Joint(limb, parentLocal, childLocal, rotation));
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
		float childRotation = getTransform().getRotation() + joint.getRotation();
		if (flip) {
			childRotation = flipAxisAngle * 2 - childRotation;
		}
		Transform childTransform = childLimb.getTransform();
		childTransform.setRotation(childRotation);
		Vector2 parentJointGlobal = getTransform().toGlobal(joint.getParentLocal());
		childTransform.setGlobal(joint.getChildLocal(), parentJointGlobal);
		childLimb.update(flip, childRotation);
	}

}
