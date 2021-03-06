package dclib.epf.graphics

import dclib.epf.Entity
import dclib.epf.EntityManager
import dclib.epf.EntitySystem
import dclib.epf.parts.ColorChangePart
import dclib.epf.parts.SpritePart

class ColorChangeSystem(entityManager: EntityManager) : EntitySystem(entityManager) {
    override val isIncremental = false

    override fun update(delta: Float, entity: Entity) {
        val colorChangePart = entity.tryGet(ColorChangePart::class)
        if (colorChangePart != null) {
            val toColor = colorChangePart.toColor
            val elapsedPercent = colorChangePart.changeTimer.elapsedPercent
            colorChangePart.changeTimer.tick(delta)
            val color = colorChangePart.fromColor.cpy().lerp(toColor.r, toColor.g, toColor.b, toColor.a,
                    elapsedPercent)
            val sprite = entity[SpritePart::class].sprite
            sprite.color = color
        }
    }
}
