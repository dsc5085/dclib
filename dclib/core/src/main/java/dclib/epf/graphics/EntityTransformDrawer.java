package dclib.epf.graphics;

import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

import dclib.epf.Entity;
import dclib.epf.parts.TransformPart;
import dclib.physics.DefaultTransform;

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
		Matrix4 renderMatrix = new Matrix4(camera.combined);
		renderMatrix.scale(pixelsPerUnit, pixelsPerUnit, 1);
		shapeRenderer.setProjectionMatrix(renderMatrix);
		shapeRenderer.begin(ShapeType.Line);
		for (Entity entity : entities) {
			draw(entity);
		}
		shapeRenderer.end();
	}

	private void draw(final Entity entity) {
		TransformPart transformPart = entity.tryGet(TransformPart.class);
		if (transformPart != null) {
			if (transformPart.getTransform() instanceof DefaultTransform) {
				DefaultTransform transform = (DefaultTransform)transformPart.getTransform();
				float[] transformedVertices = transform.getTransformedVertices();
				shapeRenderer.polygon(transformedVertices);
			}
		}
	}

}
