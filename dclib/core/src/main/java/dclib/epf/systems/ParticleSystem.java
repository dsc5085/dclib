package dclib.epf.systems;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import dclib.epf.Entity;
import dclib.epf.EntitySystem;
import dclib.epf.parts.Attachment;
import dclib.epf.parts.ParticlesPart;
import dclib.epf.parts.TransformPart;
import dclib.geometry.PolygonUtils;
import dclib.geometry.UnitConverter;

public final class ParticleSystem extends EntitySystem {
	
	private final UnitConverter unitConverter;
	
	public ParticleSystem(final UnitConverter unitConverter) {
		this.unitConverter = unitConverter;
	}
	
	@Override
	public final void update(final float delta, final Entity entity) {
		if (entity.hasActive(ParticlesPart.class)) {
			List<Attachment<ParticleEffect>> attachments = entity.get(ParticlesPart.class).getAttachments();
			Polygon polygon = entity.get(TransformPart.class).getPolygon();
			for (Attachment<ParticleEffect> attachment : attachments) {
				update(delta, polygon, attachment);
			}
		}
	}

	private void update(final float delta, final Polygon polygon, final Attachment<ParticleEffect> attachment) {
		Vector2 position = unitConverter.toPixelUnits(PolygonUtils.toGlobal(attachment.getLocalPosition(), polygon));
		ParticleEffect particleEffect = attachment.getObject();
		particleEffect.setPosition(position.x, position.y);
		particleEffect.update(delta);
		// Makes emitter duration infinite
		for (ParticleEmitter emitter : particleEffect.getEmitters()) {
			emitter.durationTimer = 0;
		}
	}

}
