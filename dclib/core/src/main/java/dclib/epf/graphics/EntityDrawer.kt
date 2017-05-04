package dclib.epf.graphics

import dclib.epf.Entity

interface EntityDrawer {
    fun getName(): String
    fun draw(entities: Collection<Entity>)
}