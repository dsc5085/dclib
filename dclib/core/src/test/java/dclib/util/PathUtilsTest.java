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

}
