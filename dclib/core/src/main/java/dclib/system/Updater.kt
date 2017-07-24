package dclib.system

interface Updater {
    val isIncremental get() = true

    fun update(delta: Float)
}
