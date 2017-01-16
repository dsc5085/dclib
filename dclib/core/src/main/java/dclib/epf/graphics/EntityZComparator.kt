package dclib.epf.graphics

import dclib.epf.Entity
import dclib.epf.parts.TransformPart
import java.util.*

class EntityZComparator : Comparator<Entity> {
    override fun compare(e1: Entity, e2: Entity): Int {
        return getValue(e1).compareTo(getValue(e2))
    }

    private fun getValue(entity: Entity): Float {
        val transformPart = entity.tryGet(TransformPart::class)
        return if (transformPart != null) transformPart.transform.z else 0f
    }
}