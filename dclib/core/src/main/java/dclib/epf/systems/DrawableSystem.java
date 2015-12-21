package dclib.epf.systems;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntitySystem;
import dclib.epf.parts.DrawablePart;
import dclib.epf.parts.TransformPart;
import dclib.geometry.UnitConverter;

public final class DrawableSystem extends EntitySystem {
	
	private final UnitConverter unitConverter;
	
	public DrawableSystem(final UnitConverter unitConverter) {
		this.unitConverter = unitConverter;
	}
	
	@Override
	public final void updateEntity(final float delta, final Entity entity) {
		if (entity.hasActive(DrawablePart.class) && entity.hasActive(TransformPart.class)) {
			TransformPart transformPart = entity.get(TransformPart.class);
			DrawablePart drawablePart = entity.get(DrawablePart.class);
			PolygonSprite sprite = drawablePart.getSprite();
			Vector2 size = unitConverter.worldToPixel(transformPart.getSize());
			sprite.setSize(size.x, size.y);
			Vector2 position = unitConverter.worldToPixel(transformPart.getPosition());
			sprite.setPosition(position.x, position.y);
			Vector2 origin = unitConverter.worldToPixel(transformPart.getOrigin());
			sprite.setOrigin(origin.x, origin.y);
			sprite.setRotation(transformPart.getRotation());
		}
	}
	
}
