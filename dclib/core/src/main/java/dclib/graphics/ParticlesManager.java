package dclib.graphics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import dclib.geometry.UnitConverter;

public final class ParticlesManager {

	private final TextureCache textureCache;
	private final Camera camera;
	private final Batch spriteBatch;
	private final UnitConverter unitConverter;
	// TODO: split this into another class
	private final List<ParticleEffect> particleEffects = new ArrayList<ParticleEffect>();

	public ParticlesManager(final TextureCache textureCache, final Camera camera, final Batch spriteBatch,
			final UnitConverter unitConverter) {
		this.textureCache = textureCache;
		this.camera = camera;
		this.spriteBatch = spriteBatch;
		this.unitConverter = unitConverter;
	}

	public final void createEffect(final String particleEffectPath, final Vector2 position) {
		ParticleEffect effect = new ParticleEffect();
		TextureAtlas atlas = textureCache.getAtlas("objects");
		effect.load(Gdx.files.internal("particles/" + particleEffectPath), atlas);
		effect.scaleEffect(unitConverter.getPixelsPerUnit());
		Vector2 pixelPosition = unitConverter.toPixelUnits(position);
		effect.setPosition(pixelPosition.x, pixelPosition.y);
		effect.start();
		particleEffects.add(effect);
	}

	public final void update(final float delta) {
		for (ParticleEffect particleEffect : new ArrayList<ParticleEffect>(particleEffects)) {
			particleEffect.update(delta);
			if (particleEffect.isComplete()) {
				particleEffects.remove(particleEffect);
			}
		}
	}

	public final void draw() {
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (ParticleEffect particleEffect : particleEffects) {
			particleEffect.draw(spriteBatch);
		}
		spriteBatch.end();
	}

}
