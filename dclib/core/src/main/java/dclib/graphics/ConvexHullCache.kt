package dclib.graphics

import com.badlogic.gdx.math.Vector2
import dclib.geometry.PolygonUtils

// TODO: Combine with textureCache?
class ConvexHullCache(private val textureCache: TextureCache) {
    private val convexHulls = mutableMapOf<String, FloatArray>()

    fun create(regionName: String): HullData {
        val region = textureCache.getPolygonRegion(regionName)
        if (!convexHulls.containsKey(regionName)) {
            val convexHull = TextureUtils.createConvexHull(region.region)
            convexHulls.put(regionName, convexHull)
        }
        return HullData(convexHulls[regionName]!!, region)
    }

    fun create(regionName: String, size: Vector2): HullData {
        val hullData = create(regionName)
        PolygonUtils.setSize(hullData.hull, size)
        return hullData
    }
}
