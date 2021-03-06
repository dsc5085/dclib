package dclib.physics

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.Joint
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Shape
import com.badlogic.gdx.physics.box2d.World
import dclib.epf.Entity
import dclib.epf.parts.TransformPart
import dclib.geometry.PolygonUtils
import net.dermetfan.gdx.physics.box2d.Box2DUtils

object Box2dUtils {
	/**
	 * Maximum rounding error for Box2D body positions.
	 */
	val ROUNDING_ERROR = 0.02f
    val POSITION_ITERATIONS = 3
    val VELOCITY_ITERATIONS = 8

    fun copy(body: Body): Body {
        val def = Box2DUtils.createDef(body)
        val copiedBody = body.world.createBody(def)
        for (fixture in body.fixtureList) {
            val clonedFixture = Box2DUtils.clone(fixture, copiedBody, true)
            clonedFixture.filterData = fixture.filterData
            val shape = clonedFixture.shape
            if (shape is PolygonShape) {
                shape.set(Box2DUtils.vertices(fixture))
            }
        }
        copiedBody.position.set(body.position)
        copiedBody.linearVelocity.set(body.linearVelocity)
        return copiedBody
    }

    fun createStaticBody(world: World, vertices: FloatArray): Body {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        val body = world.createBody(bodyDef)
        for (partitionVertices in PolygonUtils.partition(vertices)) {
            val shape = PolygonShape()
            shape.set(partitionVertices)
            body.createFixture(shape, 1f)
            shape.dispose()
        }
        return body
    }

    fun createDynamicBody(world: World, vertices: FloatArray, sensor: Boolean = false): Body {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        val body = world.createBody(bodyDef)
        for (partitionVertices in PolygonUtils.partition(vertices)) {
            val shape = PolygonShape()
            shape.set(partitionVertices)
            val fixture = body.createFixture(shape, 1f)
            fixture.isSensor = sensor
            shape.dispose()
        }
        return body
    }

    fun scale(body: Body, scale: Vector2) {
        for (fixture in body.fixtureList) {
            val shape = fixture.shape
            scale(scale, shape)
        }
    }

    fun getImpulseToReachVelocity(currentVelocity: Float, targetVelocity: Float, mass: Float): Float {
        return mass * (targetVelocity - currentVelocity)
    }

	fun getBody(entity: Entity): Body? {
        val transform = entity[TransformPart::class].transform
		return if (transform is Box2dTransform) transform.body else null
	}

    fun toGroup(value: Enum<*>): Short {
        return (value.ordinal + 1).toShort()
    }

    fun setFilter(body: Body, category: Short? = null, mask: Short? = null, group: Short? = null) {
		for (fixture in body.fixtureList) {
            val filter = fixture.filterData
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

    fun setSensor(body: Body, isSensor: Boolean) {
        for (fixture in body.fixtureList) {
            fixture.isSensor = isSensor
        }
    }

    fun setDensity(body: Body, density: Float) {
        for (fixture in body.fixtureList) {
            fixture.density = density
        }
        body.resetMassData()
    }

    fun setFriction(body: Body, friction: Float) {
        for (fixture in body.fixtureList) {
            fixture.friction = friction
        }
    }

    fun destroyJoint(joint: Joint) {
        joint.bodyA.jointList.removeAll { it.joint === joint }
        joint.bodyB.jointList.removeAll { it.joint === joint }
        joint.bodyA.world.destroyJoint(joint)
    }

    private fun scale(scale: Vector2, shape: Shape) {
        when (shape.type) {
            Shape.Type.Polygon -> {
                val polygonShape = shape as PolygonShape
                // Get the cached vertices from when the shape was first created
                val vertices = Box2DUtils.vertices(polygonShape).clone()
                PolygonUtils.scale(vertices, scale)
                polygonShape.set(vertices)
            }
            else -> {
                throw UnsupportedOperationException("${shape.type} is an invalid shape type to scale")
            }
        }
    }
}