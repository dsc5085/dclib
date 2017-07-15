package dclib.system

interface Updater {
    val maxDelta: Float
        get() = Float.MAX_VALUE

    fun update(delta: Float)
}
