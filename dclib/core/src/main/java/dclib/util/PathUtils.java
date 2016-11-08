package dclib.util;

public final class PathUtils {

	private PathUtils() {
	}
	
	public static final String internalToAbsolutePath(final String internalPath) {
		return System.getProperty("user.dir") + "/" + internalPath;
	}
	
}
