package dclib.particles

import com.badlogic.gdx.graphics.g2d.ParticleEmitter
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Fixture

data class ParticleCollidedEvent(
        val particle: ParticleEmitter.Particle,
        val velocity: Vector2,
        val staticFixture: Fixture,
        val point: Vector2,
        val normalAngle: Float)