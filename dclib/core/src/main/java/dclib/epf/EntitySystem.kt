package dclib.epf

import dclib.system.Updater

abstract class EntitySystem(private val entityManager: EntityManager) : Updater {
    override fun update(delta: Float) {
        for (entity in entityManager.getAll()) {
            if (entity.isActive) {
                update(delta, entity)
            }
        }
    }

    protected abstract fun update(delta: Float, entity: Entity)
}