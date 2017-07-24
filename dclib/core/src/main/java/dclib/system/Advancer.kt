package dclib.system

import java.util.Arrays

class Advancer(vararg updaters: Updater) {
    /**
     * Breaks deltas up into small chunks. Crucial for mechanics such as physics.
     */
    private val MAX_INCREMENTAL_DELTA = 0.01f

    var deltaScale = 1f

    private val updaters = Arrays.asList(*updaters)
    private var accumulatedDelta = 0f

    fun advance(delta: Float) {
        val maxFrameDelta = 0.25f
        val totalDelta = accumulatedDelta + Math.min(delta, maxFrameDelta) * deltaScale
        accumulatedDelta = totalDelta % MAX_INCREMENTAL_DELTA
        val currentDelta = totalDelta - accumulatedDelta
        forceAdvance(currentDelta)
    }

    fun forceAdvance(delta: Float) {
        var remainingDelta = delta
        while (remainingDelta > 0) {
            val incrementalDelta = Math.min(remainingDelta, MAX_INCREMENTAL_DELTA)
            update(incrementalDelta, true)
            remainingDelta -= incrementalDelta
        }
        update(delta, false)
    }

    private fun update(delta: Float, isIncremental: Boolean) {
        for (updater in updaters.filter { it.isIncremental == isIncremental }) {
            updater.update(delta)
        }
    }
}
