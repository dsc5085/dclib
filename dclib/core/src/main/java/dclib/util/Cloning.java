package dclib.util;

import com.rits.cloning.Cloner;

public final class Cloning {

	private static Cloner cloner = null;
	
	private Cloning() {
	}
	
	public static final <T> T clone(final T object) {
		return getCloner().deepClone(object);
	}
	
	private static final Cloner getCloner() {
		if (cloner == null) {
			cloner = new Cloner();
		}
		return cloner;
	}
	
}