package dclib.epf.parts;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;

public final class DrawablePart {

	private PolygonSprite sprite;
	
	public DrawablePart(final PolygonSprite sprite) {
		this.sprite = sprite;
	}
	
	public final PolygonSprite getSprite() {
		return sprite;
	}
	
	public final void setSprite(final PolygonSprite sprite) {
		this.sprite = sprite;
	}
	
}