package dclib.physics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D;

import dclib.geometry.UnitConverter;
import dclib.graphics.TextureCache;
import dclib.system.Updater;

public final class ParticlesManager implements Updater {

	private final TextureCache textureCache;
	private final Camera camera;
	private final Batch spriteBatch;
	private final UnitConverter unitConverter;
	private final List<ParticleEffect> particleEffects = new ArrayList<ParticleEffect>();
	private final World world;

	public ParticlesManager(final TextureCache textureCache, final Camera camera, final Batch spriteBatch,
			final UnitConverter unitConverter, final World world) {
		this.textureCache = textureCache;
		this.camera = camera;
		this.spriteBatch = spriteBatch;
		this.unitConverter = unitConverter;
		this.world = world;
	}

	public final void createEffect(final String particleEffectPath, final Vector2 position) {
		ParticleEffect effect = new ParticleEffect();
		TextureAtlas atlas = textureCache.getAtlas("objects");
		effect.load(Gdx.files.internal("particles/" + particleEffectPath), atlas);
		for (ParticleEmitter emitter : effect.getEmitters()) {
			ParticleEmitterBox2D box2dEmitter = new ParticleEmitterBox2D(world, emitter);
			effect.getEmitters().add(box2dEmitter);
			effect.getEmitters().removeValue(emitter, true);
		}
		effect.setPosition(position.x, position.y);
		effect.start();
		particleEffects.add(effect);
	}

	@Override
	public final void update(final float delta) {
		for (ParticleEffect particleEffect : new ArrayList<ParticleEffect>(particleEffects)) {
			particleEffect.update(delta);
			if (particleEffect.isComplete()) {
				particleEffects.remove(particleEffect);
			}
		}
	}

	public final void draw() {
		Matrix4 renderMatrix = new Matrix4(camera.combined);
		renderMatrix.scale(unitConverter.getPixelsPerUnit(), unitConverter.getPixelsPerUnit(), 1);
		spriteBatch.setProjectionMatrix(renderMatrix);
		spriteBatch.begin();
		for (ParticleEffect particleEffect : particleEffects) {
			particleEffect.draw(spriteBatch);
		}
		spriteBatch.end();
	}

}
