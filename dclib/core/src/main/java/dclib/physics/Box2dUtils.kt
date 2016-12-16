package dclib.physics

import com.badlogic.gdx.physics.box2d.Body
import dclib.epf.Entity
import dclib.epf.parts.TransformPart

object Box2dUtils {
	/**
	 * Maximum rounding error for Box2D body positions.
	 */
	val ROUNDING_ERROR = 0.02f
    val POSITION_ITERATIONS = 3
    val VELOCITY_ITERATIONS = 8

    fun getImpulseToReachVelocity(currentVelocity: Float, targetVelocity: Float, mass: Float): Float {
        return mass * (targetVelocity - currentVelocity)
    }

	fun getBody(entity: Entity): Body? {
		val transform = entity.tryGet(TransformPart::class.java).transform
		return if (transform is Box2dTransform) transform.body else null
	}

    fun toGroup(value: Enum<*>): Short {
        return (value.ordinal + 1).toShort()
    }

    fun setFilter(body: Body, category: Short? = null, mask: Short? = null, group: Short? = null) {
		for (fixture in body.fixtureList) {
            var filter = fixture.filterData
            if (category != null) {
                filter.categoryBits = category
            }
            if (mask != null) {
                filter.maskBits = mask
            }
            if (group != null) {
                filter.groupIndex = group
            }
			fixture.filterData = filter
		}
	}
}