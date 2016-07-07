package dclib.epf.graphics;

import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import dclib.epf.Entity;
import dclib.epf.parts.TransformPart;
import dclib.geometry.VertexUtils;

public final class EntityTransformDrawer implements EntityDrawer {

	private final ShapeRenderer shapeRenderer;
	private final Camera camera;
	private final float pixelsPerUnit;
	
	public EntityTransformDrawer(final ShapeRenderer shapeRenderer, final Camera camera, final float pixelsPerUnit) {
		this.shapeRenderer = shapeRenderer;
		this.camera = camera;
		this.pixelsPerUnit = pixelsPerUnit;
	}
	
	@Override
	public final void draw(final List<Entity> entities) {
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		for (Entity entity : entities) {
			if (entity.hasActive(TransformPart.class)) {
				TransformPart transformPart = entity.get(TransformPart.class);
				float[] transformedVertices = transformPart.getTransformedVertices();
				float[] vertices = VertexUtils.scale(transformedVertices, pixelsPerUnit);
				shapeRenderer.polygon(vertices);
			}
		}
		shapeRenderer.end();
	}
	
}
