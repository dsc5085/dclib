package dclib.map

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import dclib.geometry.Point

object MapUtils {
    val BACKGROUND_INDEX = 0
    val FOREGROUND_INDEX = 2

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
}
