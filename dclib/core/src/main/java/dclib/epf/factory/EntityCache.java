package dclib.epf.factory;

import dclib.epf.Entity;

public interface EntityCache {

	Entity create(final String entityType);
	
}
