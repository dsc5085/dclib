package dclib.epf.parts;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

public final class ParticlesPart {

	private final ParticleEffect particleEffect;
	private final Vector2 localPosition;
	
	public ParticlesPart(final ParticleEffect particleEffect, final Vector2 localPosition) {
		this.particleEffect = particleEffect;
		this.localPosition = localPosition;
	}
	
	public final ParticleEffect getParticleEffect() {
		return particleEffect;
	}
	
	public final Vector2 getLocalPosition() {
		return localPosition.cpy();
	}
	
}
