package dclib.geometry;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class RectangleUtilsTest {

	@Test
	public void top_ReturnsExpected() {
		Rectangle rectangle = createRectangle();
        assertEquals(7.7f, RectangleUtils.INSTANCE.getTop(rectangle), MathUtils.FLOAT_ROUNDING_ERROR);
    }

	@Test
	public void right_ReturnsExpected() {
		Rectangle rectangle = createRectangle();
        assertEquals(5.8f, RectangleUtils.INSTANCE.getRight(rectangle), MathUtils.FLOAT_ROUNDING_ERROR);
    }

	@Test
	public void scale_ReturnsExpected() {
        Rectangle rectangle = RectangleUtils.INSTANCE.scale(createRectangle(), 3);
        assertEquals(7.5f, rectangle.height, MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(7.5f, rectangle.width, MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(9.9f, rectangle.x, MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(15.6f, rectangle.y, MathUtils.FLOAT_ROUNDING_ERROR);
	}

	private Rectangle createRectangle() {
		return new Rectangle(3.3f, 5.2f, 2.5f, 2.5f);
	}

}
