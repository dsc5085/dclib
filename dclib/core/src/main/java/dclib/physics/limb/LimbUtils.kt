package dclib.physics.limb

import dclib.epf.Entity
import dclib.epf.parts.LimbsPart
import dclib.epf.parts.TransformPart
import dclib.physics.Transform

object LimbUtils {
	fun findContainer(entities: List<Entity>, entityToFind: Entity): Entity? {
		return if(entityToFind.has(LimbsPart::class.java)) entityToFind
			else entities.firstOrNull {
				val limbsPart = it.tryGet(LimbsPart::class.java)
				limbsPart?.all.orEmpty().any { it.entity === entityToFind }
			}
	}
}