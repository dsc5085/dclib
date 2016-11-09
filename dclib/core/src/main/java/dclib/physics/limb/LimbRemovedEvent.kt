package dclib.physics.limb

import dclib.epf.Entity

data class LimbRemovedEvent(val limb: Limb, val container: Entity)