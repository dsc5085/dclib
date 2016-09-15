package dclib.physics;

import com.badlogic.gdx.physics.box2d.Body;

import dclib.epf.Entity;

public final class Contacter {

	private final Body body;
	private final Entity entity;
	
	public Contacter(final Body body, final Entity entity) {
		this.body = body;
		this.entity = entity;
	}
	
	public final Body getBody() {
		return body;
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
