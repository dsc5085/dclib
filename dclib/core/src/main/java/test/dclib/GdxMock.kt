package test.dclib

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.GL30
import org.mockito.Mockito.mock

object GdxMock {
    fun mockGl() {
        Gdx.gl = mock<GL20>(GL20::class.java)
        Gdx.gl20 = mock<GL20>(GL20::class.java)
        Gdx.gl30 = mock<GL30>(GL30::class.java)
    }
}