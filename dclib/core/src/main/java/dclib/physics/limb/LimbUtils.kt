package dclib.physics.limb

import dclib.epf.Entity
import dclib.epf.parts.LimbsPart
import dclib.epf.parts.TransformPart
import dclib.physics.Transform

object LimbUtils {
	fun findEntity(entities: List<Entity>, limb: Limb): Entity? {
		return entities.firstOrNull {
			val transformPart = it.tryGet(TransformPart::class.java)
			transformPart?.getTransform() === limb.getTransform()
		}
	}

	fun findContainer(entities: List<Entity>, entityToFind: Entity): Entity? {
		val transform = entityToFind.get(TransformPart::class.java).getTransform()
		return entities.plus(entityToFind).firstOrNull {
			val limbsPart = it.tryGet(LimbsPart::class.java)
			limbsPart?.all.orEmpty().any { it.transform === transform }
		}
	}
}