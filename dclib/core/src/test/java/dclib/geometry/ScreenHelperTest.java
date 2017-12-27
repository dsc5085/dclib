package dclib.geometry;

import com.badlogic.gdx.math.Vector2;
import dclib.graphics.ScreenHelper;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class ScreenHelperTest {

	private static ScreenHelper screenHelper;

	@BeforeClass
	public static final void oneTimeSetup() {
		screenHelper = new ScreenHelper(32, null);
	}

	@Test
	public void toPixelUnits_ReturnsExpected() {
		Vector2 worldCoord = new Vector2(5, 2.5f);
		assertEquals(new Vector2(160, 80), screenHelper.toPixelUnits(worldCoord));
	}

}
