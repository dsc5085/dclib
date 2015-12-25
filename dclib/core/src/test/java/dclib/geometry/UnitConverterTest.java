package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public final class UnitConverterTest {
	
	private static UnitConverter unitConverter;
	
	@BeforeClass
	public static final void oneTimeSetup() {
		// TODO: replace null
		unitConverter = new UnitConverter(32, null);
	}

	@Test
	public void toPixelCoords_ValidInput_ReturnsExpected() {
		Vector2 worldCoords = new Vector2(5, 2.5f);
		assertEquals(new Vector2(160, 80), unitConverter.toPixelCoords(worldCoords));
	}
	
}
