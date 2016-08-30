package dclib.util;

import java.util.Collection;

public final class CollectionUtils {

	private CollectionUtils() {
	}

	public static final <T> boolean containsAll(final Collection<T> list, final Collection<T> values) {
		for (T value : values) {
			if (!list.contains(value)) {
				return false;
			}
		}
		return true;
	}

}
