package dclib.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public final class Box2dUtils {

	private Box2dUtils() {
	}

	public static final Body findBody(final World world, final Object userData) {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			if (body.getUserData() == userData) {
				return body;
			}
		}
		return null;
	}

}
