package dclib.epf.graphics;

import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import dclib.epf.Entity;
import dclib.epf.parts.TransformPart;
import dclib.graphics.ScreenHelper;
import dclib.physics.DefaultTransform;

public final class EntityTransformDrawer implements EntityDrawer {

	private final ShapeRenderer shapeRenderer;
	private final ScreenHelper screenHelper;

	public EntityTransformDrawer(final ShapeRenderer shapeRenderer, final ScreenHelper screenHelper) {
		this.shapeRenderer = shapeRenderer;
		this.screenHelper = screenHelper;
	}

	@Override
	public final void draw(final List<Entity> entities) {
		screenHelper.setScaledProjectionMatrix(shapeRenderer);
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
