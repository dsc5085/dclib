package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public final class RectangleUtilsTest {

	@Test
	public void top_ReturnsExpected() {
		Rectangle rectangle = createRectangle();
		assertEquals(7.7f, RectangleUtils.top(rectangle), MathUtils.FLOAT_ROUNDING_ERROR);
	}

	@Test
	public void right_ReturnsExpected() {
		Rectangle rectangle = createRectangle();
		assertEquals(5.8f, RectangleUtils.right(rectangle), MathUtils.FLOAT_ROUNDING_ERROR);
	}

	@Test
	public void scale_ReturnsExpected() {
		Rectangle rectangle = RectangleUtils.scale(createRectangle(), 3);
		assertEquals(7.5f, rectangle.height, MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(7.5f, rectangle.width, MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(9.9f, rectangle.x, MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(15.6f, rectangle.y, MathUtils.FLOAT_ROUNDING_ERROR);
	}

	private Rectangle createRectangle() {
		return new Rectangle(3.3f, 5.2f, 2.5f, 2.5f);
	}

}
