package dclib.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class MathsTest {

	@Test
	public void round_Base_ReturnsExpected() {
		assertEquals(9, Maths.round(11, 3));
	}
	
	@Test
	public void isBetween_Base_ReturnsTrue() {
		assertTrue(Maths.isBetween(5, 2, 10));
	}
	
	@Test
	public void isBetween_GreaterMin_ReturnsFalse() {
		assertFalse(Maths.isBetween(5, 6, 10));
	}
	
	@Test
	public void isBetween_LesserMax_ReturnsFalse() {
		assertFalse(Maths.isBetween(5, 2, 1));
	}
	
}
