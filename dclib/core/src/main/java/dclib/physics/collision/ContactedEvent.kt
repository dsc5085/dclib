package dclib.physics.collision

import com.badlogic.gdx.physics.box2d.Fixture

data class ContactedEvent(val fixtureA: Fixture, val fixtureB: Fixture)