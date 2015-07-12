package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public final class UnitConverterTest {
	
	private static UnitConverter unitConverter;
	
	@BeforeClass
	public static final void oneTimeSetup() {
		unitConverter = new UnitConverter(32);
	}

	@Test
	public void worldToPixel_WorldCoords_ReturnsExpected() {
		Vector2 worldCoords = new Vector2(5, 2.5f);
		assertEquals(new Vector2(160, 80), unitConverter.worldToPixel(worldCoords));
	}

	@Test
	public void pixelToWorld_XY_ReturnsExpected() {
		assertEquals(new Vector2(10.9375f, 2.5f), unitConverter.pixelToWorld(350, 80));
	}
	
}
