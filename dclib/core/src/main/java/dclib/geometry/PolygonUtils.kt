package dclib.geometry

import com.badlogic.gdx.math.*
import dclib.util.Maths
import net.dermetfan.gdx.math.BayazitDecomposer
import java.util.*

object PolygonUtils {
    val NUM_TRIANGLE_VERTICES = 3

    private val triangulator = EarClippingTriangulator()

    fun toFloats(vertices: List<Vector2>): FloatArray {
        val vertexFloats = FloatArray(vertices.size * 2)
        for (i in vertices.indices) {
            val vertex = vertices[i]
            vertexFloats[i * 2] = vertex.x
            vertexFloats[i * 2 + 1] = vertex.y
        }
        return vertexFloats
    }

    fun toVectors(vertices: FloatArray): List<Vector2> {
        val vectors = ArrayList<Vector2>()
        for (i in 0..vertices.size / 2 - 1) {
            val vector = Vector2(vertices[i * 2], vertices[i * 2 + 1])
            vectors.add(vector)
        }
        return vectors
    }

    /**
     * Converts to a polygon, setting the position based off the vertices' minimum x and y values.
     * @param vertices
     * *
     * @return polygon with calculated position
     */
    fun toPolygon(vertices: FloatArray): Polygon {
        val position = Vector2(minX(vertices), minY(vertices))
        val shiftedVertices = shift(vertices, -position.x, -position.y)
        val polygon = Polygon(shiftedVertices)
        polygon.setPosition(position.x, position.y)
        return polygon
    }

    fun minX(vertices: FloatArray): Float {
        var minX = java.lang.Float.NaN
        for (i in 0..vertices.size / 2 - 1) {
            minX = Maths.min(minX, vertices[i * 2])
        }
        return minX
    }

    fun maxX(vertices: FloatArray): Float {
        var maxX = java.lang.Float.NaN
        for (i in 0..vertices.size / 2 - 1) {
            maxX = Maths.max(maxX, vertices[i * 2])
        }
        return maxX
    }

    fun minY(vertices: FloatArray): Float {
        var minY = java.lang.Float.NaN
        for (i in 0..vertices.size / 2 - 1) {
            minY = Maths.min(minY, vertices[i * 2 + 1])
        }
        return minY
    }

    fun maxY(vertices: FloatArray): Float {
        var maxY = java.lang.Float.NaN
        for (i in 0..vertices.size / 2 - 1) {
            maxY = Maths.max(maxY, vertices[i * 2 + 1])
        }
        return maxY
    }

    fun bounds(vertices: FloatArray): Rectangle {
        val minX = minX(vertices)
        val maxX = maxX(vertices)
        val minY = minY(vertices)
        val maxY = maxY(vertices)
        return Rectangle(minX, minY, maxX - minX, maxY - minY)
    }

    fun shift(vertices: FloatArray, shiftX: Float, shiftY: Float): FloatArray {
        val shiftedVertices = FloatArray(vertices.size)
        for (i in vertices.indices) {
            if (i % 2 == 0) {
                shiftedVertices[i] = vertices[i] + shiftX
            } else {
                shiftedVertices[i] = vertices[i] + shiftY
            }
        }
        return shiftedVertices
    }

    fun setSize(vertices: FloatArray, size: Vector2) {
        val verticesSize = bounds(vertices).getSize(Vector2())
        val scale = Vector2(size.x / verticesSize.x, size.y / verticesSize.y)
        scale(vertices, scale)
    }

    fun scale(vertices: FloatArray, scale: Float) {
        scale(vertices, Vector2(scale, scale))
    }

    fun scale(vertices: FloatArray, scale: Vector2) {
        for (i in vertices.indices) {
            if (i % 2 == 0) {
                vertices[i] *= scale.x
            } else {
                vertices[i] *= scale.y
            }
        }
    }

    fun flipX(vertices: FloatArray): FloatArray {
        val flippedVertices = Arrays.copyOf(vertices, vertices.size)
        val maxX = maxX(vertices)
        for (i in 0..vertices.size / 2 - 1) {
            flippedVertices[i * 2] = maxX - vertices[i * 2]
        }
        return flippedVertices
    }

    fun flipY(vertices: FloatArray): FloatArray {
        val flippedVertices = Arrays.copyOf(vertices, vertices.size)
        val maxY = maxY(vertices)
        for (i in 0..vertices.size / 2 - 1) {
            flippedVertices[i * 2 + 1] = maxY - vertices[i * 2 + 1]
        }
        return flippedVertices
    }

    fun relativeCenter(pivot: Vector2, size: Vector2): Vector2 {
        val halfSize = size.cpy().scl(0.5f)
        return pivot.cpy().sub(halfSize)
    }

    fun copy(polygon: Polygon): Polygon {
        val copy = Polygon(polygon.vertices)
        copy.setOrigin(polygon.originX, polygon.originY)
        copy.setPosition(polygon.x, polygon.y)
        copy.rotation = polygon.rotation
        copy.setScale(polygon.scaleX, polygon.scaleY)
        return copy
    }

    fun partition(vertices: FloatArray): List<FloatArray> {
        val vectors = PolygonUtils.toVectors(vertices).toTypedArray()
        val vertexVectors = com.badlogic.gdx.utils.Array<Vector2>(vectors)
        val partitions = BayazitDecomposer.convexPartition(vertexVectors)
        return partitions.map { toFloats(it.toList()) }
    }

    fun triangulate(vertices: FloatArray): List<FloatArray> {
        val trianglesVertices = ArrayList<FloatArray>()
        val trianglesVerticesIndexes = triangulator.computeTriangles(vertices)
        val numVertices = NUM_TRIANGLE_VERTICES
        for (i in 0..trianglesVerticesIndexes.size / numVertices - 1) {
            val triangleVertices = FloatArray(numVertices * 2)
            for (j in 0..numVertices - 1) {
                val verticesStartIndex = trianglesVerticesIndexes.get(i * numVertices + j) * 2
                val triangleVerticesStartIndex = j * 2
                triangleVertices[triangleVerticesStartIndex] = vertices[verticesStartIndex]
                triangleVertices[triangleVerticesStartIndex + 1] = vertices[verticesStartIndex + 1]
            }
            trianglesVertices.add(triangleVertices)
        }
        return trianglesVertices
    }

    fun getEdges(vertices: FloatArray): List<Segment2> {
        val edges = mutableListOf<Segment2>()
        val vertexVectors = toVectors(vertices)
        for (i in 0..vertexVectors.size - 2) {
            val edge = Segment2(vertexVectors[i], vertexVectors[i + 1])
            edges.add(edge)
        }
        return edges
    }

    fun createDefault(): FloatArray {
        return floatArrayOf(0f, 0f, MathUtils.FLOAT_ROUNDING_ERROR, 0f, 0f, MathUtils.FLOAT_ROUNDING_ERROR)
    }

    fun createRectangleVertices(width: Float, height: Float): FloatArray {
        return createRectangleVertices(Rectangle(0f, 0f, width, height))
    }

    fun createRectangleVertices(rectangle: Rectangle): FloatArray {
        return floatArrayOf(
                rectangle.x, rectangle.y,
                rectangle.x + rectangle.width, rectangle.y,
                rectangle.x + rectangle.width, rectangle.y + rectangle.height,
                rectangle.x, rectangle.y + rectangle.height)
    }

    fun createCircleVertices(radius: Float, position: Vector2, numPerimeterPoints: Int): FloatArray {
        val vertices = mutableListOf<Vector2>()
        val spreadDegrees = Maths.DEGREES_MAX / numPerimeterPoints
        for (i in 0..numPerimeterPoints - 1) {
            val vertex = VectorUtils.toVector2(spreadDegrees * i, radius).add(position)
            vertices.add(vertex)
        }
        return PolygonUtils.toFloats(vertices)
    }
}
