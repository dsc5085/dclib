package dclib.epf.parts;

import javax.xml.bind.annotation.XmlRootElement;

import com.badlogic.gdx.math.Vector2;

// TODO: Merge with PhysicsPart
@XmlRootElement
public class TranslatePart {

	private Vector2 velocity = new Vector2(0, 0);

	public TranslatePart() {
	}

	public final Vector2 getVelocity() {
		return velocity.cpy();
	}

	public final void setVelocity(final Vector2 velocity) {
		this.velocity = velocity;
	}

	public final void setVelocityX(final float velocityX) {
		velocity.x = velocityX;
	}

	public final void setVelocityY(final float velocityY) {
		velocity.y = velocityY;
	}

	public final void addVelocity(final Vector2 velocity) {
		this.velocity.add(velocity);
	}

}
