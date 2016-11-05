package dclib.epf.parts

import com.badlogic.gdx.math.Vector2
import dclib.physics.Transform
import dclib.physics.limb.Limb

class LimbsPart(root: Limb) {
	val root: Limb
	var flipX = false
	var flipY = false

	val all: List<Limb>
		get() = root.descendants + root

	init {
		this.root = root
		update()
	}

	fun update() {
// TODO: Need to be able to handle both flipX and flipY at the same time
		val flipAxisAngle = (if (flipX) 90 else 0).toFloat()
		flipDescendants(flipX, flipY)
		root.update(flipX || flipY, flipAxisAngle)
	}

	private fun flipDescendants(flipX: Boolean, flipY: Boolean) {
		for (limb in root.descendants) {
			val scale = limb.transform.scale
			scale.x = Math.abs(scale.x) * (if (flipY) -1 else 1)
			scale.y = Math.abs(scale.y) * (if (flipX) -1 else 1)
			limb.transform.setScale(scale)
		}
	}
}