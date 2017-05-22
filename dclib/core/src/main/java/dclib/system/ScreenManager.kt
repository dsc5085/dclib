package dclib.system

import com.badlogic.gdx.Gdx

class ScreenManager {
    private val screens = mutableListOf<Screen>()

    operator fun contains(screen: Screen): Boolean {
        return screens.contains(screen)
    }

    fun add(screen: Screen, isShown: Boolean = true) {
        screens.add(screen)
        if (isShown) {
            screen.show()
        } else {
            screen.hide()
        }
    }

    fun remove(screen: Screen) {
        screen.hide()
        screen.dispose()
        screens.remove(screen)
    }

    fun swap(newScreen: Screen) {
        for (screen in screens.toList()) {
            remove(screen)
        }
        add(newScreen)
    }

    fun render() {
        for (screen in screens) {
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
            remove(screen)
        }
    }
}
