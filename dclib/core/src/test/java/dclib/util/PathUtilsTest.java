package dclib.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import test.dclib.io.ResourcePaths;

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
