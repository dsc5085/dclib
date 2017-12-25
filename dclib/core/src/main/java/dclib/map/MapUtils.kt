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

    fun getPixelsPerUnit(map: TiledMap): Float {
        val layer = map.layers.first() as TiledMapTileLayer
        return Math.max(layer.tileWidth, layer.tileHeight)
    }

    fun getCells(layer: TiledMapTileLayer): List<Cell> {
        val cells = mutableListOf<Cell>()
        for (x in 0 until layer.width - 1) {
            for (y in 0 until layer.height - 1) {
                val cell = layer.getCell(x, y)
                if (cell != null) {
                    cells.add(Cell(cell, Point(x, y)))
                }
            }
        }
        return cells
    }

    private fun getTileLayers(map: TiledMap): List<TiledMapTileLayer> {
        return CollectionUtils.getByType(map.layers, TiledMapTileLayer::class)
    }
}
