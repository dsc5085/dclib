package dclib.epf.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.rits.cloning.Cloner;

import dclib.epf.Entity;
import dclib.util.Converter;
import dclib.util.XmlContext;

public final class XmlEntityCache implements EntityCache {

	private final XmlContext xmlContext;
	private final Cloner cloner;
	private final EntityAdapter entityAdapter;
	private final String root;
	private final Map<String, Entity> cache = new HashMap<String, Entity>();
	
	public XmlEntityCache(final XmlContext xmlContext, final Cloner cloner, final String root, 
			final Converter[] converters) {
		this.xmlContext = xmlContext;
		this.cloner = cloner;
		this.root = root;
		entityAdapter = new EntityAdapter(converters);
	}
	
	@Override
	public final Entity create(final String entityType) {
		if (!cache.containsKey(entityType)) {
			InputStream inputStream;
			try {
				final String entityExtension = ".xml";
				inputStream = new FileInputStream(root + entityType + entityExtension);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			try {
				Entity entity = xmlContext.unmarshal(inputStream, entityAdapter);
				cache.put(entityType,  entity);
			} catch (Exception e) {
				throw new IllegalArgumentException("Could not create entity " + entityType, e);
			}
		}
		return cloner.deepClone(cache.get(entityType));
	}
	
}
