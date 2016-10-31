package dclib.util;

import java.util.Map;

public final class CollectionUtils {

	private CollectionUtils() {
	}

	public static final <TKey, TValue> TValue get(final Map<TKey, TValue> map, final TKey key,
			final TValue defaultValue) {
		if (map.containsKey(key)) {
			return map.get(key);
		} else {
			map.put(key, defaultValue);
			return defaultValue;
		}
	}

}
