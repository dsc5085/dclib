package dclib.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import dclib.util.PathUtils;

public final class TextureCacheUtils {

	private TextureCacheUtils() {
	}
	
	// TODO: Make this so you don't need to pass in ending slashes in the parameter paths
	public static final void addAtlases(final TextureCache textureCache, final String textureRootPath, 
			final String[] textureSubPaths) {
		final String tempPath = "temp/";
		final String atlasExtension = ".atlas";
		for (String textureSubPath : textureSubPaths) {
			String texturePath = textureRootPath + textureSubPath;
			String inputDir = PathUtils.internalToAbsolutePath(texturePath);
			String outputDir = Gdx.files.local(tempPath).file().getAbsolutePath();
			String name = texturePath.replace("/", "_");
			TexturePacker.process(inputDir, outputDir, name);
			String namespace = createTextureNamespace(textureRootPath, texturePath);
			textureCache.addTextureAtlas(Gdx.files.local(tempPath + name + atlasExtension), namespace);
		}
	}

	private static String createTextureNamespace(final String textureRootPath, final String texturePath) {
		return texturePath.replaceFirst(textureRootPath, "");
	}
}
