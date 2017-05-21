package dclib.system

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen

class ScreenManager {
    private val screenStates = mutableListOf<ScreenState>()

    val currentScreen: Screen
        get() {
            if (screenStates.isEmpty()) {
                throw UnsupportedOperationException("There are no screens")
            }
            return screenStates.first().screen
        }

    operator fun contains(screen: Screen): Boolean {
        return getScreens().contains(screen)
    }

    fun enable(screen: Screen) {
        screenStates.first { it.screen === screen }.isEnabled = true
        screen.show()
    }

    fun disable(screen: Screen) {
        screenStates.first { it.screen === screen }.isEnabled = false
        screen.hide()
    }

    fun add(screen: Screen, isEnabled: Boolean = true) {
        screenStates.add(ScreenState(screen, isEnabled))
        if (isEnabled) {
            enable(screen)
        } else {
            disable(screen)
        }
    }

    fun remove(screen: Screen) {
        screen.hide()
        screen.dispose()
        screenStates.removeAll { it.screen === screen }
    }

    fun swap(newScreen: Screen) {
        for (screen in getScreens()) {
            remove(screen)
        }
        add(newScreen)
    }

    fun render() {
        val enabledScreens = screenStates.filter { it.isEnabled }.map { it.screen }
        for (screen in enabledScreens) {
            screen.render(Gdx.graphics.deltaTime)
        }
    }

    fun resize(width: Int, height: Int) {
        for (screen in getScreens()) {
            screen.resize(width, height)
        }
    }

    fun dispose() {
        for (screen in getScreens()) {
            remove(screen)
        }
    }

    private fun getScreens(): List<Screen> {
        return screenStates.map { it.screen }.toList()
    }

    private inner class ScreenState(val screen: Screen, var isEnabled: Boolean)
}
