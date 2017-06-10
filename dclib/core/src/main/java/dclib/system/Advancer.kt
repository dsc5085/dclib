package dclib.system

import java.util.*

class Advancer(vararg updaters: Updater) {
    var speed = 1f

    private val updaters: List<Updater>
    private var accumulatedDelta = 0f

    init {
        this.updaters = Arrays.asList(*updaters)
    }

    fun advance(delta: Float) {
        val adjustedDelta = delta * speed
        val maxFrameDelta = 0.25f * speed
        accumulatedDelta += Math.min(adjustedDelta, maxFrameDelta)
        while (accumulatedDelta >= MAX_UPDATE_DELTA) {
            update(MAX_UPDATE_DELTA)
            accumulatedDelta -= MAX_UPDATE_DELTA
        }
    }

    private fun update(delta: Float) {
        for (updater in updaters) {
            updater.update(delta)
        }
    }

    companion object {
        val MAX_UPDATE_DELTA = 0.01f
    }
}
