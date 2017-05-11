package dclib.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen

class ScreenManager {
    private val screens = mutableListOf<Screen>()

    val currentScreen: Screen
        get() {
            if (screens.isEmpty()) {
                throw UnsupportedOperationException("There are no screens")
            }
            return screens.first()
        }

    operator fun contains(screen: Screen): Boolean {
        return screens.contains(screen)
    }

    fun add(screen: Screen) {
        screens.add(screen)
        screen.show()
    }

    fun addHidden(screen: Screen) {
        screens.add(screen)
        screen.hide()
    }

    fun remove(screen: Screen) {
        screen.hide()
        screen.dispose()
        screens.remove(screen)
    }

    fun swap(newScreen: Screen) {
        for (screen in getScreens()) {
            remove(screen)
        }
        add(newScreen)
    }

    fun render() {
        for (screen in getScreens()) {
            screen.render(Gdx.graphics.deltaTime)
        }
    }

    fun resize(width: Int, height: Int) {
        for (screen in screens) {
            screen.resize(width, height)
        }
    }

    fun dispose() {
        for (screen in screens) {
            screen.dispose()
        }
    }

    private fun getScreens(): List<Screen> {
        return screens.toList()
    }
}
