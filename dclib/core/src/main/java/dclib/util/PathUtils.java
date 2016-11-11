package dclib.util;

public final class PathUtils {

	private PathUtils() {
	}
	
	public static final String internalToAbsolutePath(final String internalPath) {
		// TODO: Does this work with Android?
		return System.getProperty("user.dir") + "/" + internalPath;
	}

}
