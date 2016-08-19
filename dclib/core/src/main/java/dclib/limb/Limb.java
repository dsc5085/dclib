package dclib.limb;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
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

	public final Rectangle getBounds() {
		return new Rectangle(polygon.getBoundingRectangle());
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

	public final void update(final boolean flipX, final boolean flipY) {
		for (Joint joint : joints) {
			update(joint, flipX, flipY);
		}
	}

	private void update(final Joint joint, final boolean flipX, final boolean flipY) {
		Limb childLimb = joint.getLimb();
		float childRotation = polygon.getRotation() + joint.getRotation();
		if (flipX) {
			childRotation = getFlippedRotation(childRotation, 90);
		}
		if (flipY) {
			childRotation = getFlippedRotation(childRotation, 0);
		}
		childLimb.polygon.setRotation(childRotation);
		Vector2 parentJointGlobal = PolygonUtils.toGlobal(joint.getParentLocal(), polygon);
		PolygonUtils.setGlobal(childLimb.polygon, joint.getChildLocal(), parentJointGlobal);
		childLimb.update(flipX, flipY);
	}

	private float getFlippedRotation(final float childRotation, final float flipAxisAngle) {
		return flipAxisAngle * 2 - childRotation;
	}

}
