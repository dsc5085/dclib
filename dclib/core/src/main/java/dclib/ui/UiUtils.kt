package dclib.ui

import com.badlogic.gdx.scenes.scene2d.ui.TextField

object UiUtils {
    fun hideBackground(textField: TextField) {
        textField.style.background = null
        textField.invalidateHierarchy()
    }
}