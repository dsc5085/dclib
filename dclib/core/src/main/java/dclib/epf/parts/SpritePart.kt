package dclib.epf.parts

import com.badlogic.gdx.graphics.g2d.PolygonRegion
import com.badlogic.gdx.graphics.g2d.PolygonSprite

class SpritePart(val sprite: PolygonSprite) {
    constructor(region: PolygonRegion) : this(PolygonSprite(region))
}