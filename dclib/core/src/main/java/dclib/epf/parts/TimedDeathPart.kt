package dclib.epf.parts

import dclib.util.Timer

class TimedDeathPart(deathTime: Float) {
    val isDead get() = deathTimer.isElapsed

    private val deathTimer = Timer(deathTime)

    fun update(delta: Float) {
        deathTimer.tick(delta)
    }
}
