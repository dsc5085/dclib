package dclib.system.io;

import com.badlogic.gdx.files.FileHandle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public final class FileUtils {

    private FileUtils() {
    }

    public static final InputStream internalPathToInputStream(final String internalPath) {
        String path = PathUtils.internalToAbsolutePath(internalPath);
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File does not exist at " + internalPath, e);
        }
    }

    public static final FileOutputStream internalPathToOutputStream(final String internalPath) {
        String path = PathUtils.internalToAbsolutePath(internalPath);
        try {
            return new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File does not exist at " + internalPath, e);
        }
    }

    public static final FileHandle internalPathToFileHandle(final String internalPath) {
        String path = PathUtils.internalToAbsolutePath(internalPath);
        return new FileHandle(path);
    }

}
