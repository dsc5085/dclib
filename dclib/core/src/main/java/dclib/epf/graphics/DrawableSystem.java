package dclib.epf.graphics;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntitySystem;
import dclib.epf.parts.DrawablePart;
import dclib.epf.parts.TransformPart;
import dclib.geometry.Transform;
import dclib.geometry.UnitConverter;

public final class DrawableSystem extends EntitySystem {

	private final UnitConverter unitConverter;

	public DrawableSystem(final EntityManager entityManager, final UnitConverter unitConverter) {
		super(entityManager);
		this.unitConverter = unitConverter;
	}

	// TODO: Need to be public for unit test.  Update unit test and make this protected
	@Override
	public final void update(final float delta, final Entity entity) {
		DrawablePart drawablePart = entity.tryGet(DrawablePart.class);
		if (drawablePart != null) {
			Transform transform = entity.get(TransformPart.class).getTransform();
			PolygonSprite sprite = drawablePart.getSprite();
			Vector2 origin = unitConverter.toPixelUnits(transform.getOrigin());
			sprite.setOrigin(origin.x, origin.y);
			Vector2 size = unitConverter.toPixelUnits(transform.getSize());
			sprite.setSize(size.x, size.y);
			Vector2 scale = transform.getScale();
			sprite.setScale(scale.x, scale.y);
			Vector2 position = unitConverter.toPixelUnits(transform.getPosition());
			sprite.setPosition(position.x, position.y);
			sprite.setRotation(transform.getRotation());
		}
	}

}
