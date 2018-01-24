package dclib.util

class Timer @JvmOverloads constructor(var maxTime: Float = 0f, private var time: Float = 0f) {
    val remainingTime: Float
        get() = maxTime - time

    val isRunning: Boolean
        get() = time > 0

    val isElapsed: Boolean
        get() = time >= maxTime

    val elapsedPercent: Float
        get() = Math.min(time / maxTime, 1f)

    fun reset() {
        time = 0f
    }

    fun check(): Boolean {
        val isElapsed = isElapsed
        if (isElapsed) {
            reset()
        }
        return isElapsed
    }

    fun tick(delta: Float) {
        time = Math.min(time + delta, maxTime)
    }
}
