package dclib.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public final class TimerTest {
	
	private Timer timer;
	
	@Before
	public void setUp() {
		timer = new Timer(2.5f);
	}
	
	@Test
	public void getRemainingTime_Ticked_ReturnsExpected() {
		timer.tick(0.25f);
		assertEquals(2.25f, timer.getRemainingTime(), 0);
	}
	
	@Test
	public void isElapsed_Elapsed_ReturnsTrue() {
		timer.tick(3.2f);
		assertTrue(timer.isElapsed());
	}
	
	@Test
	public void isElapsed_NotElapsed_ReturnsFalse() {
		timer.tick(0.25f);
		assertFalse(timer.isElapsed());
	}
	
	@Test
	public void getElapsedPercent_Ticked_ReturnsExpected() {
		timer.tick(0.25f);
		assertEquals(0.1f, timer.getElapsedPercent(), 0);
	}
	
	@Test
	public void tick_GreaterThanMaxTime_BoundToMaxTime() {
		timer.tick(3.2f);
		assertEquals(0, timer.getRemainingTime(), 0);
	}
	
}
