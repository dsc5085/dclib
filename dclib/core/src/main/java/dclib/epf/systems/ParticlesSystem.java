package dclib.epf.systems;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntitySystem;
import dclib.epf.parts.ParticlesPart;
import dclib.epf.parts.TransformPart;
import dclib.geometry.PolygonUtils;
import dclib.geometry.UnitConverter;

public final class ParticlesSystem extends EntitySystem {
	
	private final UnitConverter unitConverter;
	
	public ParticlesSystem(final UnitConverter unitConverter) {
		this.unitConverter = unitConverter;
	}
	
	@Override
	public final void updateEntity(final float delta, final Entity entity) {
		if (entity.hasActive(ParticlesPart.class)) {
			ParticlesPart particlesPart = entity.get(ParticlesPart.class);
			Vector2 localPosition = particlesPart.getLocalPosition();
			Polygon polygon = entity.get(TransformPart.class).getPolygon();
			Vector2 position = unitConverter.toPixelUnits(PolygonUtils.toGlobal(localPosition, polygon));
			particlesPart.getParticleEffect().setPosition(position.x, position.y);
			particlesPart.getParticleEffect().update(delta);
		}
	}

}
