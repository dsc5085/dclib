package dclib.graphics

import com.badlogic.gdx.math.Vector2
import dclib.geometry.PolygonUtils

class ConvexHullCache(private val textureCache: TextureCache) {
    private val convexHulls = mutableMapOf<String, FloatArray>()

    fun create(regionName: String, size: Vector2): HullData {
        val region = textureCache.getPolygonRegion(regionName)
        if (!convexHulls.containsKey(regionName)) {
            val convexHull = TextureUtils.createConvexHull(region.region)
            convexHulls.put(regionName, convexHull)
        }
        val vertices = PolygonUtils.setSize(convexHulls[regionName]!!, size)
        return HullData(vertices, region)
    }
}
