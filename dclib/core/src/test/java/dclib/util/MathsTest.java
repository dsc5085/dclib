package dclib.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class MathsTest {

	@Test
	public void round_Base_ReturnsExpected() {
		int actual = Maths.round(11, 3);
		assertEquals(9, actual);
	}
	
	@Test
	public void isBetween_Base_ReturnsTrue() {
		boolean actual = Maths.isBetween(5, 2, 10);
		assertTrue(actual);
	}
	
	@Test
	public void isBetween_GreaterMin_ReturnsFalse() {
		boolean actual = Maths.isBetween(5, 6, 10);
		assertFalse(actual);
	}
	
	@Test
	public void isBetween_LesserMax_ReturnsFalse() {
		boolean actual = Maths.isBetween(5, 2, 1);
		assertFalse(actual);
	}
	
}
