package dclib.parts;

import org.junit.Test;

import test.dclib.geometry.TestPolygonFactory;
import test.dclib.geometry.VectorTestUtils;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.parts.TransformPart;

public final class TransformPartTest {

	@Test
	public void constructor_UpdatesSize() {
		TransformPart transformPart = createTransformPart();
		VectorTestUtils.assertEquals(10, 4, transformPart.getSize());
	}

	@Test
	public void getCenter_ReturnsExpected() {
		TransformPart transformPart = createTransformPart();
		VectorTestUtils.assertEquals(2, 6, transformPart.getCenter());
	}

	@Test
	public void setCenter_UpdatesCenter() {
		TransformPart transformPart = createTransformPart();
		transformPart.setCenter(new Vector2(1, 2));
		VectorTestUtils.assertEquals(1, 2, transformPart.getCenter());
	}

	@Test
	public void setCenteredRotation_DoesNotUpdateCenter() {
		TransformPart transformPart = createTransformPart();
		transformPart.setCenteredRotation(63);
		VectorTestUtils.assertEquals(2, 6, transformPart.getCenter());
	}

	@Test
	public void translate_UpdatesPosition() {
		TransformPart transformPart = createTransformPart();
		transformPart.translate(new Vector2(-9, 1.5f));
		VectorTestUtils.assertEquals(-8, 3.5f, transformPart.getPosition());
	}

	private TransformPart createTransformPart() {
		Polygon polygon = TestPolygonFactory.createSimpleRectangle();
		return new TransformPart(polygon, 1);
	}

}
