package dclib.system

import java.util.*

class Advancer(vararg updaters: Updater) {
    private val MAX_UPDATE_DELTA = 0.01f

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
        // TODO: Remove the MAX_UPDATE_DELTA logic. Make it specific to individual processors
        while (accumulatedDelta >= MAX_UPDATE_DELTA) {
            update(MAX_UPDATE_DELTA)
            accumulatedDelta -= MAX_UPDATE_DELTA
        }
    }

    fun forceAdvance(delta: Float) {
        var remainingDelta = delta
        while (remainingDelta > 0) {
            val maxUpdateDelta = Math.min(remainingDelta, MAX_UPDATE_DELTA)
            update(maxUpdateDelta)
            remainingDelta -= maxUpdateDelta
        }
    }

    private fun update(delta: Float) {
        for (updater in updaters) {
            updater.update(delta)
        }
    }
}
