package dclib.epf.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.DrawablePart;
import dclib.epf.parts.TransformPart;

public final class EntityDrawer {

	private boolean drawPolygons = false;
	private final EntityManager entityManager;
	private final PolygonSpriteBatch spriteBatch;
	private final ShapeRenderer shapeRenderer = new ShapeRenderer();
	private final Camera camera;
	private final float pixelsPerUnit;
	
	public EntityDrawer(final EntityManager entityManager, final PolygonSpriteBatch spriteBatch, final Camera camera, 
			final float pixelsPerUnit) {
		this.entityManager = entityManager;
		this.spriteBatch = spriteBatch;
		this.camera = camera;
		this.pixelsPerUnit = pixelsPerUnit;
	}
	
	public final void setPolygonDrawing(final boolean drawPolygons) {
		this.drawPolygons = drawPolygons;
	}
	
	public final void draw() {
		List<Entity> entities = entityManager.getAll();
		final EntityZComparator entityZComparator = new EntityZComparator();
		Collections.sort(entities, entityZComparator);
		drawSprites(entities);
		if (drawPolygons) {
			drawPolygons(entities);
		}
	}
	
	private void drawSprites(final List<Entity> entities) {
		spriteBatch.setProjectionMatrix(camera.combined);
		spriteBatch.begin();
		for (Entity entity : entities) {
			if (entity.hasActive(DrawablePart.class)) {
				DrawablePart drawablePart = entity.get(DrawablePart.class);
				drawablePart.getSprite().draw(spriteBatch);
			}
		}
		spriteBatch.end();
	}
	
	private void drawPolygons(final List<Entity> entities) {
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		for (Entity entity : entities) {
			if (entity.hasActive(TransformPart.class)) {
				TransformPart transformPart = entity.get(TransformPart.class);
				float[] transformedVertices = transformPart.getPolygon().getTransformedVertices();
				float[] vertices = new float[transformedVertices.length];
				for (int i = 0; i < transformedVertices.length; i++) {
					vertices[i] = transformedVertices[i] * pixelsPerUnit;
				}
				shapeRenderer.polygon(vertices);
			}
		}
		shapeRenderer.end();
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
