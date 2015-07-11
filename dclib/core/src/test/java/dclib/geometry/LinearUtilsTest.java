package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class LinearUtilsTest {

	@Test
	public void distance_AnyParameters_ReturnsExpected() {
		assertEquals(15, LinearUtils.distance(3, 5), 0);
	}
	
	@Test
	public void relativeMiddle_AnyParameters_ReturnsExpected() {
		assertEquals(0.5f, LinearUtils.relativeMiddle(4, 3), 0);
	}
	
}
