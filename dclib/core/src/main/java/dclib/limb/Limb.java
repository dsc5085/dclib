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
		this(new Polygon());
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

	public final Limb addJoint(final Limb limb, final float parentLocalX, final float parentLocalY,
			final float childLocalX, final float childLocalY, final float rotation) {
		return addJoint(new Joint(limb, new Vector2(parentLocalX, parentLocalY), new Vector2(childLocalX, childLocalY),
				rotation));
	}

	public final Limb addJoint(final Joint joint) {
		joints.add(joint);
		update(joint);
		return this;
	}

	public final void update() {
		for (Joint joint : joints) {
			update(joint);
		}
	}

	private void update(final Joint joint) {
		Limb childLimb = joint.getLimb();
		float childRotation = polygon.getRotation() + joint.getRotation();
		childLimb.polygon.setRotation(childRotation);
		Vector2 parentJointGlobal = PolygonUtils.toGlobal(joint.getParentLocal(), polygon);
		PolygonUtils.setGlobal(childLimb.polygon, joint.getChildLocal(), parentJointGlobal);
		childLimb.update();
	}

}
