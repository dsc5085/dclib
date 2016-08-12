package dclib.epf.graphics;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import dclib.epf.Entity;
import dclib.epf.parts.Attachment;
import dclib.epf.parts.DrawablePart;
import dclib.epf.parts.ParticlesPart;
import dclib.epf.parts.TransformPart;

public final class EntitySpriteDrawer implements EntityDrawer {

	private final PolygonSpriteBatch spriteBatch;
	private final Camera camera;

	public EntitySpriteDrawer(final PolygonSpriteBatch spriteBatch, final Camera camera) {
		this.spriteBatch = spriteBatch;
		this.camera = camera;
	}

	@Override
	public final void draw(final List<Entity> entities) {
		final EntityZComparator entityZComparator = new EntityZComparator();
		Collections.sort(entities, entityZComparator);
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (Entity entity : entities) {
			if (entity.hasActive(DrawablePart.class)) {
				DrawablePart drawablePart = entity.get(DrawablePart.class);
				drawablePart.getSprite().draw(spriteBatch);
			}
			if (entity.hasActive(ParticlesPart.class)) {
				List<Attachment<ParticleEffect>> attachments = entity.get(ParticlesPart.class).getAttachments();
				for (Attachment<ParticleEffect> attachment : attachments) {
					attachment.getObject().draw(spriteBatch);
				}
			}
		}
		spriteBatch.end();
	}

	private static class EntityZComparator implements Comparator<Entity> {

		@Override
		public final int compare(final Entity e1, final Entity e2) {
			return Float.compare(getValue(e1), getValue(e2));
		}

		private float getValue(final Entity entity) {
			if (entity.hasActive(DrawablePart.class)) {
				return entity.get(TransformPart.class).getZ();
			} else {
				return 0;
			}
		}

	}

}
