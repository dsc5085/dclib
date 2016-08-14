package dclib.physics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dclib.geometry.PolygonUtils;

public final class Limb {

	private final Polygon polygon;
	private final Vector2 parentJointLocal;
	private final List<Joint> joints;

	public Limb(final Polygon polygon, final Vector2 parentJointLocal) {
		this(polygon, parentJointLocal, new ArrayList<Joint>());
	}

	public Limb(final Polygon polygon, final Vector2 parentJointLocal, final List<Joint> joints) {
		this.polygon = polygon;
		this.parentJointLocal = parentJointLocal;
		this.joints = joints;
	}

	public final void update() {
		for (Joint joint : joints) {
			Limb childLimb = joint.getLimb();
			Vector2 jointToChildGlobal = PolygonUtils.toGlobal(joint.getLocal(), polygon);
			PolygonUtils.setGlobal(childLimb.polygon, childLimb.parentJointLocal, jointToChildGlobal);
			childLimb.update();
		}
	}

}
