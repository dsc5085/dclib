package dclib.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

import dclib.epf.Entity;

public final class Contacter {

	private final Fixture fixture;
	private final Entity entity;

	public Contacter(final Fixture fixture, final Entity entity) {
		this.fixture = fixture;
		this.entity = entity;
	}

	public final Fixture getFixture() {
		return fixture;
	}

	public final Body getBody() {
		return fixture.getBody();
	}

	public final Entity getEntity() {
		return entity;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (obj instanceof Contacter) {
			Contacter other = (Contacter)obj;
			return entity == other.entity;
		}
		return false;
	}

}
