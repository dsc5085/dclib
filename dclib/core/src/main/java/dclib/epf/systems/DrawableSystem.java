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
	public final void initialize(final Entity entity) {
		update(entity);
	}

	@Override
	public final void update(final float delta, final Entity entity) {
		update(entity);
	}

	private void update(final Entity entity) {
		if (entity.hasActive(DrawablePart.class)) {
			TransformPart transformPart = entity.get(TransformPart.class);
			DrawablePart drawablePart = entity.get(DrawablePart.class);
			PolygonSprite sprite = drawablePart.getSprite();
			Vector2 origin = unitConverter.toPixelUnits(transformPart.getOrigin());
			sprite.setOrigin(origin.x, origin.y);
			Vector2 size = unitConverter.toPixelUnits(transformPart.getSize());
			sprite.setSize(size.x, size.y);
			Vector2 position = unitConverter.toPixelUnits(transformPart.getPosition());
			sprite.setPosition(position.x, position.y);
			sprite.setRotation(transformPart.getRotation());
		}
	}

}
