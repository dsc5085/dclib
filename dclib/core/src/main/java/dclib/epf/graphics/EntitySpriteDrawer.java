package dclib.epf.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import dclib.epf.Entity;
import dclib.epf.EntityAddedListener;
import dclib.epf.EntityManager;
import dclib.epf.parts.SpritePart;
import dclib.epf.parts.TransformPart;
import dclib.graphics.ScreenHelper;

public final class EntitySpriteDrawer implements EntityDrawer {

	private final PolygonSpriteBatch spriteBatch;
	private final ScreenHelper screenHelper;
	// Don't draw new entities because their transforms might not have been initialized
	private final List<Entity> newEntities = new ArrayList<Entity>();

	public EntitySpriteDrawer(final PolygonSpriteBatch spriteBatch, final ScreenHelper screenHelper,
			final EntityManager entityManager) {
		this.spriteBatch = spriteBatch;
		this.screenHelper = screenHelper;
		entityManager.listen(new EntityAddedListener() {
			@Override
			public void added(final Entity entity) {
				newEntities.add(entity);
			}
		});
	}

	@Override
	public final void draw(final List<Entity> entities) {
		final EntityZComparator entityZComparator = new EntityZComparator();
		Collections.sort(entities, entityZComparator);
		screenHelper.setProjectionMatrix(spriteBatch);
		spriteBatch.begin();
		for (Entity entity : entities) {
			if (newEntities.contains(entity)) {
				newEntities.remove(entity);
			} else {
				draw(entity);
			}
		}
		spriteBatch.end();
	}

	private void draw(final Entity entity) {
		SpritePart spritePart = entity.tryGet(SpritePart.class);
		if (spritePart != null) {
			spritePart.getSprite().draw(spriteBatch);
		}
	}

	private static class EntityZComparator implements Comparator<Entity> {

		@Override
		public final int compare(final Entity e1, final Entity e2) {
			return Float.compare(getValue(e1), getValue(e2));
		}

		private float getValue(final Entity entity) {
			TransformPart transformPart = entity.tryGet(TransformPart.class);
			if (transformPart != null) {
				return transformPart.getTransform().getZ();
			} else {
				return 0;
			}
		}

	}

}
