package dclib.system

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport

abstract class Screen : ScreenAdapter() {
    internal var isUpdating = true
    internal var isDrawing = true

    protected val stage = Stage(ScreenViewport())

    private val input = Input()

    init {
        add(stage)
    }

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
            stage.draw()
        }
        stage.act(delta)
    }

    override fun dispose() {
        stage.dispose()
    }

    protected open fun update(delta: Float) {
    }

    protected open fun draw() {
    }

    protected fun add(processor: InputProcessor) {
        input.add(processor)
    }
}