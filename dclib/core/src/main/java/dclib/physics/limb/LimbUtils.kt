package dclib.physics.limb

import dclib.epf.Entity
import dclib.epf.parts.LimbsPart
import dclib.epf.parts.TransformPart
import dclib.physics.Transform

// TODO: Make these extension methods of Limb
object LimbUtils {
	fun findEntity(entities: List<Entity>, limb: Limb): Entity? {
		return entities.firstOrNull {
			val transformPart = it.tryGet(TransformPart::class.java)
			transformPart?.transform === limb.transform
		}
	}

	fun findContainer(entities: List<Entity>, entityToFind: Entity): Entity? {
		val transform = entityToFind[TransformPart::class.java].transform
		return if(entityToFind.has(LimbsPart::class.java)) entityToFind
			else entities.firstOrNull {
				val limbsPart = it.tryGet(LimbsPart::class.java)
				val limbs = limbsPart?.all.orEmpty()
				limbs.any { it.transform === transform }
			}
	}
}