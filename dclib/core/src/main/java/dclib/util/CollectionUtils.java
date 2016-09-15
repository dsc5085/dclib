package dclib.util;

import java.util.Collection;
import java.util.Map;

public final class CollectionUtils {

	private CollectionUtils() {
	}

	public static final <T> boolean containsAny(final Collection<T> list, final Collection<T> values) {
		for (T value : values) {
			if (list.contains(value)) {
				return true;
			}
		}
		return false;
	}

	public static final <T> boolean containsAll(final Collection<T> list, final Collection<T> values) {
		for (T value : values) {
			if (!list.contains(value)) {
				return false;
			}
		}
		return true;
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
