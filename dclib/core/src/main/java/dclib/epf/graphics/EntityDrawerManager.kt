package dclib.epf.graphics

import dclib.epf.Entity

class EntityDrawerManager(drawers: List<EntityDrawer>) {
    private val drawers: List<EntityDrawerData>

    init {
        this.drawers = drawers.map { EntityDrawerData(it, true) }
    }

    fun enableDrawer(name: String) {
        drawers.single { it.drawer.getName() == name }.isEnabled = true
    }

    fun disableDrawer(name: String) {
        drawers.single { it.drawer.getName() == name }.isEnabled = false
    }

    fun draw(entities: Collection<Entity>) {
        for (drawer in drawers.filter { it.isEnabled }) {
            drawer.drawer.draw(entities)
        }
    }

    private data class EntityDrawerData(val drawer: EntityDrawer, var isEnabled: Boolean)
}