package dclib.epf.graphics

class DrawerManager(drawers: List<Drawer>, enabledDrawerNames: List<String>) {
    private val drawers: List<DrawerData>

    init {
        this.drawers = drawers.map { DrawerData(it, false) }
        for (enabledDrawerName in enabledDrawerNames) {
            enableDrawer(enabledDrawerName)
        }
    }

    fun enableDrawer(name: String) {
        drawers.single { it.drawer.getName() == name }.isEnabled = true
    }

    fun disableDrawer(name: String) {
        drawers.single { it.drawer.getName() == name }.isEnabled = false
    }

    fun draw() {
        for (drawer in drawers.filter { it.isEnabled }) {
            drawer.drawer.draw()
        }
    }

    private data class DrawerData(val drawer: Drawer, var isEnabled: Boolean)
}