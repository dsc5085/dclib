package dclib.epf.parts

import dclib.eventing.Property
import dclib.physics.Transform

class TransformPart(transform: Transform) {
    val transformChanged get() = transformProperty.changed
    private val transformProperty = Property(transform)
    var transform
        get() = transformProperty.value
        set(value) { transformProperty.value = value }
}