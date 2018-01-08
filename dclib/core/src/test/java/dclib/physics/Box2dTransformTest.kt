package dclib.physics

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import dclib.geometry.PolygonUtils
import org.junit.Assert.assertEquals
import org.junit.Test

class JumpVelocitySolverTest {
    @Test
    fun setLocalToWorld_IsCorrect() {
        val size = Vector2(1.1058195f, 2.1807468f)
        val world = World(Vector2(0f, -10f), true)
        val vertices = PolygonUtils.createRectangleVertices(size.x, size.y)
        val body = Box2dUtils.createDynamicBody(world, vertices)
        val transform = Box2dTransform(body)
        transform.setLocalToWorld(Vector2(size.x, 0f), Vector2(8f, 2f))
        assertEquals(Rectangle(6.8941803f, 2f, size.x, size.y), transform.bounds)
    }
}