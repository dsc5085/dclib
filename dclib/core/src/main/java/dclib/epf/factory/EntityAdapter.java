package dclib.epf.factory;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import dclib.epf.Entity;
import dclib.util.Converter;

/**
 * Used to marshal/unmarshal between the serializable {@link EntityAdapted} and {@link Entity}.
 * @author David Chen
 *
 */
public final class EntityAdapter extends XmlAdapter<EntityAdapted, Entity> {
	
	private final Converter[] converters;
	
	public EntityAdapter() {
		this(new Converter[0]);
	}
	
	public EntityAdapter(final Converter[] converters) {
		this.converters = converters;
	}

	@Override
	public final EntityAdapted marshal(final Entity entity) {
		EntityAdapted entityAdapted = new EntityAdapted(entity.getAll());
		return entityAdapted;
	}

	@Override
	public final Entity unmarshal(final EntityAdapted entityAdapted) {
		Entity entity = new Entity();
		for (Object part : entityAdapted.getParts()) {
			for (Converter converter : converters) {
				if (converter.canConvert(part)) {
					part = converter.convert(part);
				}
			}
			entity.attach(part);
		}
		return entity;
	}

}
