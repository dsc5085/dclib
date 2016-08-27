package dclib.limb;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dclib.geometry.PolygonUtils;

public final class Limb {

	private Polygon polygon;
	private final List<Joint> joints = new ArrayList<Joint>();

	public Limb() {
		polygon = null;
	}

	public Limb(final Polygon polygon) {
		this.polygon = polygon;
	}

	public final Polygon getPolygon() {
		return polygon;
	}

	public final void setPolygon(final Polygon polygon) {
		this.polygon = polygon;
	}

	public final Limb remove(final Polygon descendantPolygon) {
		if (polygon == descendantPolygon) {
			return this;
		}
		for (Joint joint : joints) {
			Polygon polygon = joint.getLimb().getPolygon();
			if (polygon == descendantPolygon) {
				joints.remove(joint);
				return joint.getLimb();
			}
			Limb foundLimb = joint.getLimb().remove(descendantPolygon);
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
