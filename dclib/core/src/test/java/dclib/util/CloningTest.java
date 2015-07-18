package dclib.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.math.Rectangle;

public final class CloningTest {

	@Test
	public void clone_CustomClass_ReturnsExpected() {
		Rectangle rectangle = new Rectangle(1.5f, 2.2f, 10.2f, 3.9f);
		assertEquals(rectangle, Cloning.clone(rectangle));
	}
	
}
