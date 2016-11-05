package dclib.physics.limb

import dclib.eventing.Event

class LimbRemovedEvent(limb: Limb) : Event<LimbRemovedListener> {
	public val limb: Limb = limb
	
	override fun notify(listener: LimbRemovedListener) {
		listener.removed(limb)
	}
}