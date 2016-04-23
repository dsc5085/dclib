package dclib.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.math.Polygon;

public final class PolygonFactoryTest {
	
	private static Polygon polygon;
	private static Polygon polygonCopy;
	
	@BeforeClass
	public static void oneTimeSetUp() {
		polygon = createPolygon();
		polygonCopy = PolygonFactory.copy(polygon);
	}
	
	@Test
	public void copy_Polygon_HasSameVertices() {
		assertEquals(polygon.getVertices(), polygonCopy.getVertices());
	}
	
	@Test
	public void copy_Polygon_HasSameOriginX() {
		assertEquals(polygon.getOriginX(), polygonCopy.getOriginX(), 0);
	}
	
	@Test
	public void copy_Polygon_HasSameOriginY() {
		assertEquals(polygon.getOriginY(), polygonCopy.getOriginY(), 0);
	}
	
	@Test
	public void copy_Polygon_HasSameScaleX() {
		assertEquals(polygon.getScaleX(), polygonCopy.getScaleX(), 0);
	}
	
	@Test
	public void copy_Polygon_HasSameScaleY() {
		assertEquals(polygon.getScaleY(), polygonCopy.getScaleY(), 0);
	}
	
	@Test
	public void copy_Polygon_HasSameX() {
		assertEquals(polygon.getX(), polygonCopy.getX(), 0);
	}
	
	@Test
	public void copy_Polygon_HasSameY() {
		assertEquals(polygon.getY(), polygonCopy.getY(), 0);
	}
	
	@Test
	public void copy_Polygon_HasSameRotation() {
		assertEquals(polygon.getRotation(), polygonCopy.getRotation(), 0);
	}
	
	@Test
	public void createRectangle_WidthAndHeight_ReturnsExpected() {
		float[] rectangleVertices = PolygonFactory.createRectangleVertices(5, 3);
		float[] expected = new float[] { 0, 0, 5, 0, 5, 3, 0, 3 };
		assertTrue(Arrays.equals(expected, rectangleVertices));
	}
	
	@Test
	public void createRectangle_PositionAndSize_ReturnsExpected() {
		float[] rectangleVertices = PolygonFactory.createRectangleVertices(2, 2, 5, 3);
		float[] expected = new float[] { 2, 2, 7, 2, 7, 5, 2, 5 };
		assertTrue(Arrays.equals(expected, rectangleVertices));
	}
	
	private static Polygon createPolygon() {
		float[] vertices = new float[] { 0, 0, 2, 0, 2, 1 };
		Polygon polygon = new Polygon(vertices);
		polygon.setOrigin(1, 2);
		polygon.setScale(10, 4);
		polygon.setPosition(5, 4);
		polygon.setRotation(45);
		return polygon;
	}

}
