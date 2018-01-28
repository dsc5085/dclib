package dclib.util

class Timer @JvmOverloads constructor(var maxTime: Float = 0f, var elapsedTime: Float = 0f) {
    val remainingTime: Float
        get() = maxTime - elapsedTime

    val isRunning: Boolean
        get() = elapsedTime > 0

    val isElapsed: Boolean
        get() = elapsedTime >= maxTime

    val elapsedPercent: Float
        get() = Math.min(elapsedTime / maxTime, 1f)

    fun reset() {
        elapsedTime = 0f
    }

    fun check(): Boolean {
        val isElapsed = isElapsed
        if (isElapsed) {
            reset()
        }
        return isElapsed
    }

    fun tick(delta: Float) {
        elapsedTime = Math.min(elapsedTime + delta, maxTime)
    }
}
