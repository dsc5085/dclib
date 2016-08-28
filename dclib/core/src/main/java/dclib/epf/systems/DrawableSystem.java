package dclib.epf.systems;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.parts.DrawablePart;
import dclib.epf.parts.TransformPart;
import dclib.geometry.UnitConverter;

public final class DrawableSystem extends EntitySystem {

	private final UnitConverter unitConverter;

	public DrawableSystem(final EntityManager entityManager, final UnitConverter unitConverter) {
		super(entityManager);
		this.unitConverter = unitConverter;
	}

	@Override
	protected final void update(final float delta, final Entity entity) {
		DrawablePart drawablePart = entity.tryGet(DrawablePart.class);
		if (drawablePart != null) {
			TransformPart transformPart = entity.get(TransformPart.class);
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
