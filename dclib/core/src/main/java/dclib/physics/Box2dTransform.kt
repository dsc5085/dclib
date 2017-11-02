package dclib.physics

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import dclib.geometry.center
import net.dermetfan.gdx.physics.box2d.Box2DUtils

class Box2dTransform : Transform {
    val body: Body

    override val origin get() = Vector2()

    private var _scale: Vector2 = Vector2(1f, 1f)
    override val scale get() = _scale.cpy()

    override val localSize get() = Box2DUtils.size(body).cpy()

    override var position
        get() = body.position.cpy()
        set(value) = body.setTransform(value, body.angle)

    override var rotation
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
        body = Box2dUtils.copy(other.body)
        setScale(other.scale)
    }

    constructor(body: Body) : this(body, 0f)

    constructor(body: Body, z: Float) : super(z) {
        this.body = body
    }

    constructor(other: Transform, world: World)
            : this(Box2dTransform(Box2dUtils.createDynamicBody(world, other.getVertices()))) {
        position = other.position
        rotation = other.rotation
    }

    override fun getVertices(): FloatArray {
        val fixture = body.fixtureList.get(0)
        return Box2DUtils.vertices(fixture)
    }

    override fun setScale(scale: Vector2) {
        val signEquals = Math.signum(_scale.x) == Math.signum(scale.x)
                && Math.signum(_scale.y) == Math.signum(scale.y)
        if (!signEquals || !_scale.epsilonEquals(scale, MathUtils.FLOAT_ROUNDING_ERROR)) {
            Box2dUtils.scale(body, scale)
            _scale.set(scale)
            body.resetMassData()
        }
    }

    override fun applyImpulse(impulse: Vector2) {
        body.applyLinearImpulse(impulse, position, true)
    }
}
