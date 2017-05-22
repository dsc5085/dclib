package dclib.system

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ScreenAdapter

abstract class Screen : ScreenAdapter() {
    internal var isUpdating = true
    internal var isDrawing = true

    private val input = Input()

    override fun show() {
        resume()
        isDrawing = true
    }

    override fun hide() {
        pause()
        isDrawing = false
    }

    override fun resume() {
        isUpdating = true
        input.enable()
    }

    override fun pause() {
        isUpdating = false
        input.disable()
    }

    override fun render(delta: Float) {
        if (isUpdating) {
            update(delta)
        }
        if (isDrawing) {
            draw()
        }
    }

    protected abstract fun update(delta: Float)

    protected abstract fun draw()

    protected fun add(processor: InputProcessor) {
        input.add(processor)
    }
}