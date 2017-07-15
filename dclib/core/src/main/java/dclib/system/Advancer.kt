package dclib.system

import dclib.util.Maths
import java.util.*

class Advancer(vararg updaters: Updater) {
    private val MAX_ACCUMULATED_DELTA = 0.01f

    var deltaModifier = 1f

    private val updaters = Arrays.asList(*updaters)
    private var accumulatedDelta = 0f

    fun advance(delta: Float) {
        val maxFrameDelta = 0.25f
        val currentFrameDelta = Math.min(delta, maxFrameDelta) * deltaModifier
        val totalDelta = currentFrameDelta + accumulatedDelta
        val updateDelta = Maths.round(totalDelta, MAX_ACCUMULATED_DELTA)
        accumulatedDelta = totalDelta - updateDelta
        update(updateDelta)
    }

    fun update(delta: Float) {
        for (updater in updaters) {
            updater.update(delta)
        }
    }
}
