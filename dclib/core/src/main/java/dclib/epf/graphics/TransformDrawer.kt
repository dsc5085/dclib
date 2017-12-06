package dclib.epf.graphics

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.parts.TransformPart
import dclib.graphics.ScreenHelper
import dclib.physics.DefaultTransform

class TransformDrawer(
        private val entityManager: EntityManager,
        private val shapeRenderer: ShapeRenderer,
        private val screenHelper: ScreenHelper
) : Drawer {
    override fun getName(): String {
        return "transform"
    }

    override fun draw() {
        screenHelper.setScaledProjectionMatrix(shapeRenderer)
        shapeRenderer.begin(ShapeType.Line)
        for (entity in entityManager.getAll()) {
            draw(entity)
        }
        shapeRenderer.end()
    }

    private fun draw(entity: Entity) {
        val transformPart = entity.tryGet(TransformPart::class)
        if (transformPart != null) {
            val transform = transformPart.transform
            if (transform is DefaultTransform) {
                shapeRenderer.polygon(transform.transformedVertices)
            }
        }
    }
}
