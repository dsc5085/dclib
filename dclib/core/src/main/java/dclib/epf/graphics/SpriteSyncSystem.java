package dclib.epf.graphics;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntityManager;
import dclib.epf.EntitySystem;
import dclib.epf.parts.SpritePart;
import dclib.epf.parts.TransformPart;
import dclib.graphics.ScreenHelper;
import dclib.physics.Transform;

public final class SpriteSyncSystem extends EntitySystem {

	private final ScreenHelper screenHelper;

	public SpriteSyncSystem(final EntityManager entityManager, final ScreenHelper screenHelper) {
		super(entityManager);
		this.screenHelper = screenHelper;
	}

	// TODO: Need to be public for unit test.  Update unit test and make this protected
	@Override
	public final void update(final float delta, final Entity entity) {
		SpritePart spritePart = entity.tryGet(SpritePart.class);
		if (spritePart != null) {
			Transform transform = entity.get(TransformPart.class).getTransform();
			PolygonSprite sprite = spritePart.getSprite();
			Vector2 origin = screenHelper.toPixelUnits(transform.getOrigin());
			sprite.setOrigin(origin.x, origin.y);
			Vector2 size = screenHelper.toPixelUnits(transform.getSize());
			sprite.setSize(size.x, size.y);
			Vector2 scale = transform.getScale();
			sprite.setScale(scale.x, scale.y);
			Vector2 position = screenHelper.toPixelUnits(transform.getPosition());
			sprite.setPosition(position.x, position.y);
			sprite.setRotation(transform.getRotation());
		}
	}

}
