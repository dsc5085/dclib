package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import dclib.graphics.ScreenHelper;

public final class ScreenHelperTest {

	private static ScreenHelper screenHelper;

	@BeforeClass
	public static final void oneTimeSetup() {
		screenHelper = new ScreenHelper(32);
	}

	@Test
	public void toPixelUnits_ReturnsExpected() {
		Vector2 worldCoords = new Vector2(5, 2.5f);
		assertEquals(new Vector2(160, 80), screenHelper.toPixelUnits(worldCoords));
	}

}
