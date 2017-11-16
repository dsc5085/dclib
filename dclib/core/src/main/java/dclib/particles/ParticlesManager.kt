package dclib.particles

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.World
import dclib.graphics.ScreenHelper
import dclib.graphics.TextureCache
import dclib.system.Updater
import java.util.ArrayList

class ParticlesManager(
        private val textureCache: TextureCache,
        private val spriteBatch: Batch,
        private val screenHelper: ScreenHelper,
        private val world: World
) : Updater {
    override val isIncremental = false

    private val particleEffectDatas = ArrayList<ParticleEffectData>()

    fun createEffect(particleEffectPath: String, positionGetter: PositionGetter): ParticleEffect {
        val effect = ParticleEffect()
        val atlas = textureCache.getAtlas("objects")
        effect.load(Gdx.files.internal("particles/" + particleEffectPath), atlas)
        for (emitter in effect.emitters) {
            val box2dEmitter = ParticleEmitterBox2d(world, emitter, this::filterContact)
            effect.emitters.add(box2dEmitter)
            effect.emitters.removeValue(emitter, true)
        }
        particleEffectDatas.add(ParticleEffectData(effect, positionGetter))
        return effect
    }

    override fun update(delta: Float) {
        for (particleEffectData in ArrayList(particleEffectDatas)) {
            val particleEffect = particleEffectData.effect
            val position = particleEffectData.positionGetter.get()
            if (particleEffect.isComplete || position == null) {
                particleEffectDatas.remove(particleEffectData)
            }
            if (position != null) {
                particleEffect.setPosition(position.x, position.y)
            }
            particleEffect.update(delta)
        }
    }

    fun draw() {
        screenHelper.setScaledProjectionMatrix(spriteBatch)
        spriteBatch.begin()
        for (particleEffectData in particleEffectDatas) {
            particleEffectData.effect.draw(spriteBatch)
        }
        spriteBatch.end()
    }

    private fun filterContact(fixture: Fixture): Boolean {
        return fixture.body.type == BodyDef.BodyType.StaticBody
    }

    private data class ParticleEffectData(
            val effect: ParticleEffect,
            val positionGetter: PositionGetter)
}
