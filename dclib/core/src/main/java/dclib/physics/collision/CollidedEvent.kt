package dclib.physics.collision

import dclib.epf.Entity

data class CollidedEvent(val source: Entity, val target: Entity) {
    val collisions = mutableListOf<Collision>()
}