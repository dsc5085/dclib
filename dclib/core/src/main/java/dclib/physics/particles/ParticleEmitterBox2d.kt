package dclib.physics.particles

import com.badlogic.gdx.graphics.g2d.ParticleEmitter
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.RayCastCallback
import com.badlogic.gdx.physics.box2d.World
import dclib.eventing.EventDelegate

/**
 * Modified from com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D
 */

/** @author kalle_h
 * *
 * *         ParticleEmitterBox2d use box2d rayCast:ing to achieve continuous collision detection against box2d fixtures. If
 * *         particle detect collision it change it's direction before actual collision would occur. Velocity is 100% reflected.
 * *
 * *         These particles does not have any other physical attributes or functionality. Particles can't collide to other
 * *         particles.
 */
class ParticleEmitterBox2d
/** Constructs ParticleEmitterBox2d fully copying given emitter attributes. Box2d World is used for rayCasting. Assumes that
 * particles use same unit system that box2d world does.

 * @param world
 * *
 * @param emitter
 */
(internal val world: World?, emitter: ParticleEmitter, filterContact: (Fixture) -> Boolean) : ParticleEmitter(emitter) {
    val particleCollidedDelegate = EventDelegate<ParticleCollidedEvent>()

    internal val startPoint = Vector2()
    internal val endPoint = Vector2()
    /** collision flag  */
    internal var particleCollided = false
    internal var normalAngle = 0f
    internal var staticFixture: Fixture? = null
    internal var point = Vector2()

    /** default visibility to prevent synthetic accessor creation  */
    internal val rayCallBack: RayCastCallback = RayCastCallback { fixture, point, normal, fraction ->
        particleCollided = filterContact(fixture)
        if (particleCollided) {
            normalAngle = MathUtils.atan2(normal.y, normal.x) * MathUtils.radiansToDegrees
            staticFixture = fixture
            this.point = point
            fraction
        } else {
            -1f
        }
    }

    override fun newParticle(sprite: Sprite): Particle {
        return ParticleBox2d(sprite)
    }

    /** Particle that can collide to box2d fixtures  */
    private inner class ParticleBox2d(sprite: Sprite) : Particle(sprite) {

        /** translate particle given amount. Continuous collision detection achieved by using RayCast from oldPos to newPos.

         * @param velocityX
         * *
         * @param velocityY
         */
        override fun translate(velocityX: Float, velocityY: Float) {
            var newVelocityX = velocityX
            var newVelocityY = velocityY

            /** Position offset is half of sprite texture size.  */
            val x = x + width / 2f
            val y = y + height / 2f

            /** collision flag to false  */
            particleCollided = false
            startPoint.set(x, y)
            endPoint.set(x + velocityX, y + velocityY)

            /** If velocities squares summed is shorter than Epsilon it could lead ~0 length rayCast that cause nasty c++ assertion
             * inside box2d. This is so short distance that moving particle has no effect so this return early.  */
            if (velocityX * velocityX + velocityY * velocityY >= EPSILON) {
                world?.rayCast(rayCallBack, startPoint, endPoint)
            }

            /** If ray collided boolean has set to true at rayCallBack  */
            if (particleCollided) {
                // perfect reflection
                angle = 2f * normalAngle - angle - 180f
                angleCos = MathUtils.cosDeg(angle)
                angleSin = MathUtils.sinDeg(angle)
                newVelocityX *= angleCos
                newVelocityY *= angleSin
                currentLife = 0
                particleCollidedDelegate.notify(ParticleCollidedEvent(this, Vector2(velocityX, velocityY), staticFixture!!, point))
            }

            super.translate(newVelocityX, newVelocityY)
            super.setRotation(Vector2(newVelocityX, newVelocityY).angle())
        }
    }

    companion object {
        /** If velocities squared is shorter than this it could lead 0 length rayCast that cause c++ assertion at box2d  */
        private val EPSILON = 0.001f
    }
}
