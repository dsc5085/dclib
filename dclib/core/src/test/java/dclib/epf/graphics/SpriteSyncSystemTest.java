package dclib.epf.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import dclib.epf.DefaultEntityManager;
import dclib.epf.Entity;
import dclib.epf.parts.SpritePart;
import dclib.epf.parts.TransformPart;
import dclib.graphics.ScreenHelper;
import dclib.graphics.TextureUtils;
import dclib.physics.DefaultTransform;
import dclib.physics.Transform;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import test.dclib.GdxTestRunner;
import test.dclib.geometry.TestPolygonFactory;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class SpriteSyncSystemTest {

	private static SpriteSyncSystem system;

	@BeforeClass
	public static final void oneTimeSetUp() {
		ScreenHelper screenHelper = new ScreenHelper(2, null, null);
		system = new SpriteSyncSystem(new DefaultEntityManager(), screenHelper);
	}

	@Test
	public void update_UpdatesSprite() {
		Entity entity = createEntity();
		PolygonSprite sprite = entity.get(SpritePart.class).getSprite();
		system.update(1, entity);
		assertEquals(20, sprite.getWidth(), MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(8, sprite.getHeight(), MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(2, sprite.getX(), MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(4, sprite.getY(), MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(2, sprite.getOriginX(), MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(2, sprite.getOriginY(), MathUtils.FLOAT_ROUNDING_ERROR);
		assertEquals(90, sprite.getRotation(), MathUtils.FLOAT_ROUNDING_ERROR);
	}

	private Entity createEntity() {
		Entity entity = new Entity();
		Transform transform = new DefaultTransform(1, TestPolygonFactory.createSimpleRectangle());
		entity.attach(new TransformPart(transform));
		PolygonRegion polygonRegion = createPolygonRegion(8, 8);
		entity.attach(new SpritePart(polygonRegion));
		return entity;
	}

	private PolygonRegion createPolygonRegion(final int width, final int height) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		TextureRegion textureRegion = new TextureRegion(new Texture(pixmap));
		return TextureUtils.createPolygonRegion(textureRegion);
	}

}
