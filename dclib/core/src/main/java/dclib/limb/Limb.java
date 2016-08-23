package dclib.limb;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dclib.geometry.PolygonUtils;

public final class Limb {

	private final Polygon polygon;
	private final List<Joint> joints = new ArrayList<Joint>();

	public Limb() {
		this(new Polygon());
	}

	public Limb(final Polygon polygon) {
		this.polygon = polygon;
	}

	public final Polygon getPolygon() {
		return polygon;
	}

	public final List<Polygon> getDescendants() {
		List<Polygon> descendants = new ArrayList<Polygon>();
		for (Joint joint : joints) {
			descendants.add(joint.getLimb().polygon);
			descendants.addAll(joint.getLimb().getDescendants());
		}
		return descendants;
	}

	public final void translate(final float x, final float y) {
		polygon.translate(x, y);
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
		float childRotation = polygon.getRotation() + joint.getRotation();
		if (flip) {
			childRotation = flipAxisAngle * 2 - childRotation;
		}
		childLimb.polygon.setRotation(childRotation);
		Vector2 parentJointGlobal = PolygonUtils.toGlobal(joint.getParentLocal(), polygon);
		PolygonUtils.setGlobal(childLimb.polygon, joint.getChildLocal(), parentJointGlobal);
		childLimb.update(flip, childRotation);
	}

}
