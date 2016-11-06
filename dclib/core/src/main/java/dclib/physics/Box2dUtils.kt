package dclib.physics

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Filter
import dclib.epf.Entity
import dclib.epf.parts.TransformPart

object Box2dUtils {
	/**
	 * Maximum rounding error for Box2D body positions.
	 */
	val ROUNDING_ERROR = 0.02f

	fun getBody(entity: Entity): Body? {
		val transform = entity.tryGet(TransformPart::class.java).transform
		return if (transform is Box2dTransform) transform.body else null
	}

	fun setFilter(body: Body, category: Short, mask: Short) {
		val filter = Filter()
		filter.categoryBits = category
		filter.maskBits = mask
		for (fixture in body.fixtureList) {
			fixture.filterData = filter
		}
	}
}