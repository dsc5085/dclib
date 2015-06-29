package dclib.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public final class PathUtilsTest {

	@Test
	public void internalToAbsolutePath_ExistingFile_ReturnsExpected() {
		String path = PathUtils.internalToAbsolutePath("test.txt");
		assertNotNull(path);
	}

	@Test(expected=IllegalArgumentException.class)
	public void internalToAbsolutePath_NonexistentFile_ThrowsException() {
		PathUtils.internalToAbsolutePath("doesnotexist.txt");
	}
	
}
