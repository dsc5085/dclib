package dclib.map

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import dclib.geometry.Point

data class Cell(val cell: TiledMapTileLayer.Cell, val point: Point)