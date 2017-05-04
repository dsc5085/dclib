package dclib.epf.graphics

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import dclib.epf.Entity
import dclib.epf.parts.TransformPart
import dclib.graphics.ScreenHelper
import dclib.physics.DefaultTransform

class EntityTransformDrawer(
        private val shapeRenderer: ShapeRenderer,
        private val screenHelper: ScreenHelper)
    : EntityDrawer {
    override fun getName(): String {
        return "transform"
    }

    override fun draw(entities: Collection<Entity>) {
        screenHelper.setScaledProjectionMatrix(shapeRenderer)
        shapeRenderer.begin(ShapeType.Line)
        for (entity in entities) {
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
