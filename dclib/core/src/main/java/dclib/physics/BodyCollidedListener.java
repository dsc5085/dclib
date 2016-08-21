package dclib.physics;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;

public interface BodyCollidedListener {

	void collided(final Entity entity, final List<Vector2> offset);

}
