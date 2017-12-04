package dclib.map

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer

object MapUtils {
    val BACKGROUND_INDEX = 0
    val FOREGROUND_INDEX = 2

    fun getPixelsPerUnit(map: TiledMap): Float {
        val layer = map.layers.first() as TiledMapTileLayer
        return Math.max(layer.tileWidth, layer.tileHeight)
    }
}
