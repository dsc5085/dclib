package dclib.physics

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.PolygonShape
import dclib.geometry.center
import net.dermetfan.gdx.physics.box2d.Box2DUtils

class Box2dTransform : Transform {
    val body: Body

    override val origin: Vector2
        get() = Vector2()

    private var _scale: Vector2 = Vector2(1f, 1f)
    override var scale: Vector2
        get() = _scale.cpy()
        set(value) {
            val signEquals = Math.signum(_scale.x) == Math.signum(value.x)
                    && Math.signum(_scale.y) == Math.signum(value.y)
            if (!signEquals || !_scale.epsilonEquals(value, MathUtils.FLOAT_ROUNDING_ERROR)) {
                Box2dUtils.scale(body, value)
                _scale.set(value)
            }
        }

    override val localSize: Vector2
        get() = Box2DUtils.size(body).cpy()

    override var position: Vector2
        get() = body.position.cpy()
        set(value) = body.setTransform(value, body.angle)

    override var rotation: Float
        get() = body.angle * MathUtils.radiansToDegrees
        set(value) = body.setTransform(body.position, value * MathUtils.degreesToRadians)

    override val bounds: Rectangle
        get() {
            val center = Box2DUtils.aabb(body).center
            val size = size
            return Rectangle(center.x - size.x / 2, center.y - size.y / 2, size.x, size.y)
        }

    override var velocity: Vector2
        get() = body.linearVelocity.cpy()
        set(value) {
            body.linearVelocity = value
        }

    constructor(other: Box2dTransform) : super(other.z) {
        val def = Box2DUtils.createDef(other.body)
        body = other.body.world.createBody(def)
        for (fixture in other.body.fixtureList) {
            val clonedFixture = Box2DUtils.clone(fixture, body, true)
            val shape = clonedFixture.shape
            if (shape is PolygonShape) {
                shape.set(Box2DUtils.vertices(fixture))
            }
        }
        scale = other.scale
    }

    constructor(body: Body) : this(body, 0f)

    constructor(body: Body, z: Float) : super(z) {
        this.body = body
    }

    override fun getVertices(): FloatArray {
        val fixture = body.fixtureList.get(0)
        return Box2DUtils.vertices(fixture)
    }

    override fun applyImpulse(impulse: Vector2) {
        body.applyLinearImpulse(impulse, position, true)
    }
}
