package test.dclib.geometry;

import com.badlogic.gdx.math.Polygon;

public final class TestPolygonFactory {

	private TestPolygonFactory() {
	}

	public static Polygon createSimpleRectangle() {
		float[] vertices = new float[] { 0, 0, 5, 0, 5, 2, 0, 2 };
		Polygon polygon = new Polygon(vertices);
		polygon.setOrigin(1, 1);
		polygon.setScale(2, 2);
		polygon.setPosition(1, 2);
		polygon.setRotation(90);
		return polygon;
	}

	public static Polygon createTriangle() {
		float[] vertices = new float[] { 0, 0, 2, 0, 2, 1 };
		Polygon polygon = new Polygon(vertices);
		polygon.setOrigin(1, 2);
		polygon.setScale(10, 4);
		polygon.setPosition(5, 4);
		polygon.setRotation(45);
		return polygon;
	}

}
