package dclib.map

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import dclib.geometry.Point
import dclib.util.CollectionUtils

object MapUtils {
    fun getBackgroundLayers(map: TiledMap): List<TiledMapTileLayer> {
        return getTileLayers(map) - getForegroundLayer(map)
    }

    fun getForegroundLayer(map: TiledMap): TiledMapTileLayer {
        return getTileLayers(map).last()
    }

    fun getTileLayers(map: TiledMap): List<TiledMapTileLayer> {
        return CollectionUtils.getByType(map.layers, TiledMapTileLayer::class)
    }

    fun getPixelsPerUnit(map: TiledMap): Float {
        val tileWidth = map.properties["tilewidth", Int::class.java]
        val tileHeight = map.properties["tileheight", Int::class.java]
        return Math.max(tileWidth, tileHeight).toFloat()
    }

    fun getPixelsPerUnit(layer: TiledMapTileLayer): Float {
        return Math.max(layer.tileWidth, layer.tileHeight)
    }

    fun getCells(layer: TiledMapTileLayer): List<Cell> {
        val cells = mutableListOf<Cell>()
        for (x in 0 until layer.width) {
            for (y in 0 until layer.height) {
                val cell = layer.getCell(x, y)
                if (cell != null) {
                    cells.add(Cell(cell, Point(x, y)))
                }
            }
        }
        return cells
    }
}
