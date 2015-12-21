package dclib.epf.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.DrawablePart;
import dclib.epf.parts.TransformPart;

public final class EntityDrawer {

	private final EntityManager entityManager;
	private final PolygonSpriteBatch spriteBatch;
	private final Camera camera;
	
	public EntityDrawer(final EntityManager entityManager, final PolygonSpriteBatch spriteBatch, final Camera camera) {
		this.entityManager = entityManager;
		this.spriteBatch = spriteBatch;
		this.camera = camera;
	}
	
	public void draw() {
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		final EntityZComparator entityZComparator = new EntityZComparator();
		List<Entity> entities = entityManager.getAll();
		Collections.sort(entities, entityZComparator);
		draw(entities);
		spriteBatch.end();
	}
	
	private void draw(final List<Entity> entities) {
		for (Entity entity : entities) {
			if (entity.hasActive(DrawablePart.class)) {
				DrawablePart drawablePart = entity.get(DrawablePart.class);
				drawablePart.getSprite().draw(spriteBatch);
			}
		}
	}
	
	private static class EntityZComparator implements Comparator<Entity> {
		
	    @Override
	    public final int compare(final Entity e1, final Entity e2) {
	    	return Float.compare(getValue(e1), getValue(e2));
	    }
	    
	    private final float getValue(final Entity entity) {
	    	if (entity.hasActive(DrawablePart.class)) {
	    		return entity.get(TransformPart.class).getZ();
	    	}
	    	else {
	    		return 0;
	    	}
	    }
	    
	}
	
}
