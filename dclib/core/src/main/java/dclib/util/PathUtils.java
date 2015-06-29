package dclib.util;

import java.net.URL;

public final class PathUtils {

	private PathUtils() {
	}
	
	public static final String internalToAbsolutePath(final String internalPath) {
		URL url = PathUtils.class.getResource("/" + internalPath);
		if (url == null) {
			throw new IllegalArgumentException("Path " + internalPath + "does not exist");
		}
		return PathUtils.class.getResource("/" + internalPath).getPath();
	}
	
}
