package dclib.util;

import java.net.URL;

public final class PathUtils {

	private PathUtils() {
	}
	
	public static final String internalToAbsolutePath(final String internalPath) {
		URL url = PathUtils.class.getResource("/" + internalPath);
		if (url == null) {
			throw new IllegalArgumentException("File does not exist at " + internalPath);
		}
		return PathUtils.class.getResource("/" + internalPath).getPath();
	}
	
}
