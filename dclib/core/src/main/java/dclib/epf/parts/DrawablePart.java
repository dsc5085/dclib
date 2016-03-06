package dclib.epf.parts;

import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;

public final class DrawablePart {

	private PolygonSprite sprite;
	
	public DrawablePart(final PolygonRegion region) {
		sprite = new PolygonSprite(region);
	}
	
	public final PolygonSprite getSprite() {
		return sprite;
	}
	
	public final void setSprite(final PolygonSprite sprite) {
		this.sprite = sprite;
	}
	
}