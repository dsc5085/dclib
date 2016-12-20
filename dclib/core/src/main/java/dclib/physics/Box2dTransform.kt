package dclib.physics

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.Shape
import dclib.geometry.PolygonUtils
import net.dermetfan.gdx.physics.box2d.Box2DUtils

class Box2dTransform : Transform {
    val body: Body
    private val scale: Vector2

    constructor(other: Box2dTransform) : super(other.z) {
        val def = Box2DUtils.createDef(other.body)
        body = other.body.world.createBody(def)
        for (fixture in other.body.fixtureList) {
            Box2DUtils.clone(fixture, body, true)
        }
        scale = other.scale
    }

    constructor(body: Body) : this(0f, body) {
    }

    constructor(z: Float, body: Body) : super(z) {
        this.body = body
        scale = Vector2(1f, 1f)
    }

    override fun getVertices(): FloatArray {
        val fixture = body.fixtureList.get(0)
        return Box2DUtils.vertices(fixture)
    }

    override fun getOrigin(): Vector2 {
        return Vector2()
    }

    override fun getScale(): Vector2 {
        return scale.cpy()
    }

    override fun setScale(scale: Vector2) {
        if (!this.scale.epsilonEquals(scale, MathUtils.FLOAT_ROUNDING_ERROR)) {
            this.scale.set(scale)
            for (fixture in body.fixtureList) {
                val shape = fixture.shape
                scale(scale, shape)
            }
        }
    }

    override fun getSize(): Vector2 {
        return Box2DUtils.size(body).cpy()
    }

    override fun getPosition(): Vector2 {
        return body.position.cpy()
    }

    override fun setPosition(position: Vector2) {
        body.setTransform(position, body.angle)
    }

    override fun getBounds(): Rectangle {
        return Rectangle(Box2DUtils.aabb(body))
    }

    override fun getRotation(): Float {
        return body.angle * MathUtils.radiansToDegrees
    }

    override fun setRotation(degrees: Float) {
        body.setTransform(body.position, degrees * MathUtils.degreesToRadians)
    }

    override fun getVelocity(): Vector2 {
        return body.linearVelocity.cpy()
    }

    override fun setVelocity(velocity: Vector2) {
        body.linearVelocity = velocity
    }

    override fun applyImpulse(impulse: Vector2) {
        body.applyLinearImpulse(impulse, position, true)
    }

    private fun scale(scale: Vector2, shape: Shape) {
        if (shape.type == Shape.Type.Polygon) {
            val polygonShape = shape as PolygonShape
            // Get the cached vertices from when the shape was first created
            val vertices = Box2DUtils.vertices(polygonShape)
            val scaledVertices = PolygonUtils.scale(vertices, scale)
            polygonShape.set(scaledVertices)
        } else {
            throw UnsupportedOperationException(shape.type.toString() + " is an invalid shape type to scale")
        }
    }
}
