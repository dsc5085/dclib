package dclib.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.badlogic.gdx.math.Rectangle;

import dclib.util.Maths;

public final class RectangleUtilsTest {

	@Test
	public void top_Rectangle_ReturnsExpected() {
		Rectangle rectangle = createRectangle();
		assertEquals(7.7f, RectangleUtils.top(rectangle), Maths.EPSILON);
	}

	@Test
	public void right_Rectangle_ReturnsExpected() {
		Rectangle rectangle = createRectangle();
		assertEquals(5.8f, RectangleUtils.right(rectangle), Maths.EPSILON);
	}
	
	@Test
	public void scale_Rectangle_ReturnsExpected() {
		Rectangle rectangle = RectangleUtils.scale(createRectangle(), 3);
		assertEquals(7.5f, rectangle.height, Maths.EPSILON);
		assertEquals(7.5f, rectangle.width, Maths.EPSILON);
		assertEquals(9.9f, rectangle.x, Maths.EPSILON);
		assertEquals(15.6f, rectangle.y, Maths.EPSILON);
	}
	
	@Test
	public void translateY_Rectangle_ReturnsExpected() {
		Rectangle rectangle = createRectangle();
		RectangleUtils.translateY(rectangle, 0.3f);
		assertEquals(2.2f, rectangle.height, Maths.EPSILON);
		assertEquals(5.5f, rectangle.y, Maths.EPSILON);
	}
	
	private Rectangle createRectangle() {
		return new Rectangle(3.3f, 5.2f, 2.5f, 2.5f);
	}
	
}
