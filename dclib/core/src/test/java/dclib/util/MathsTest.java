package dclib.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class MathsTest {

	@Test
	public void round_Base_ReturnsExpected() {
		assertEquals(9, Maths.round(11, 3));
	}

}
