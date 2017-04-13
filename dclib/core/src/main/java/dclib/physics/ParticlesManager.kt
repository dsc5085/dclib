package dclib.physics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import dclib.graphics.ScreenHelper
import dclib.graphics.TextureCache
import dclib.system.Updater
import java.util.*

class ParticlesManager(
        private val textureCache: TextureCache,
        private val spriteBatch: Batch,
        private val screenHelper: ScreenHelper,
        private val world: World
) : Updater {
    private val particleEffects = ArrayList<ParticleEffect>()

    fun createEffect(particleEffectPath: String, position: Vector2): ParticleEffect {
        val effect = ParticleEffect()
        val atlas = textureCache.getAtlas("objects")
        effect.load(Gdx.files.internal("particles/" + particleEffectPath), atlas)
        for (emitter in effect.emitters) {
            val box2dEmitter = ParticleEmitterBox2d(world, emitter, this::filterContact)
            effect.emitters.add(box2dEmitter)
            effect.emitters.removeValue(emitter, true)
        }
        effect.setPosition(position.x, position.y)
        effect.start()
        particleEffects.add(effect)
        return effect
    }

    override fun update(delta: Float) {
        for (particleEffect in ArrayList(particleEffects)) {
            particleEffect.update(delta)
            if (particleEffect.isComplete) {
                particleEffects.remove(particleEffect)
            }
        }
    }

    fun draw() {
        screenHelper.setScaledProjectionMatrix(spriteBatch)
        spriteBatch.begin()
        for (particleEffect in particleEffects) {
            particleEffect.draw(spriteBatch)
        }
        spriteBatch.end()
    }

    private fun filterContact(fixture: Fixture): Boolean {
        return fixture.body.type == BodyDef.BodyType.StaticBody
    }
}
