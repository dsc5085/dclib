package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Fixture
import com.google.common.base.Objects
import dclib.epf.Entity

class Contacter(val fixture: Fixture, val entity: Entity) {
    val body get() = fixture.body

    override fun equals(other: Any?): Boolean {
        return other is Contacter && fixture === other.fixture && entity === other.entity
    }

    override fun hashCode(): Int {
        return Objects.hashCode(fixture, entity)
    }
}
