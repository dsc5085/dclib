package dclib.physics

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import dclib.geometry.VectorUtils
import dclib.geometry.abs
import dclib.geometry.center
import dclib.geometry.div

abstract class Transform(val z: Float) {
    abstract val origin: Vector2
    abstract val scale: Vector2
    abstract val localSize: Vector2
    abstract var position: Vector2
    abstract var rotation: Float
    abstract val bounds: Rectangle
    abstract var velocity: Vector2

    val size get() = localSize.scl(scale.abs())
    val localCenter get() = localSize.scl(0.5f)
    // TODO: This is inaccurate if trying to get the "true" center
    val center get() = bounds.center

    abstract fun getVertices(): FloatArray
    abstract fun setScale(scale: Vector2)
    abstract fun applyImpulse(impulse: Vector2)

    fun translate(offset: Vector2) {
        position = position.add(offset)
    }

    fun setSize(size: Vector2) {
        val scaleMultiplier = size.div(this.size)
        setScale(scale.scl(scaleMultiplier.abs()))
    }

    /**
     * Convert a point local to the origin to a point in world space.
     * @param local local point within the untransformed polygon
     * *
     * @return world point
     */
    fun toWorld(local: Vector2): Vector2 {
        return local.cpy()
                .sub(origin)
                .scl(scale)
                .rotate(rotation)
                .add(position)
                .add(origin)
    }

    /**
     * Translate so that the local point is at the world point.
     * @param local local point
     * *
     * @param newWorld new world point
     */
    fun setLocalToWorld(local: Vector2, newWorld: Vector2) {
        val currentWorld = toWorld(local)
        setWorld(currentWorld, newWorld)
    }

    fun setWorld(currentWorld: Vector2, newWorld: Vector2) {
        val offset = VectorUtils.offset(currentWorld, newWorld)
        translate(offset)
    }
}