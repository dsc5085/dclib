package dclib.util;

import dclib.system.io.PathUtils;
import org.junit.Test;
import test.dclib.io.ResourcePaths;

import static org.junit.Assert.assertNotNull;

public final class PathUtilsTest {

	@Test
	public void internalToAbsolutePath_ExistingFile_ReturnsExpected() {
		String path = PathUtils.internalToAbsolutePath(ResourcePaths.ENTITY_XML);
		assertNotNull(path);
	}

	@Test(expected=IllegalArgumentException.class)
	public void internalToAbsolutePath_NonexistentFile_ThrowsException() {
		PathUtils.internalToAbsolutePath(ResourcePaths.NONEXISTENT_XML);
	}
	
}
