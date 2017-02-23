package dclib.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import dclib.system.io.PathUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public final class TextureCache {

	private final List<TextureRegion> textureRegions = new ArrayList<TextureRegion>();
	private final Map<String, TextureRegion> nameToTextureRegions = new HashMap<String, TextureRegion>();
    private final Map<String, TextureAtlas> nameToAtlas = new HashMap<String, TextureAtlas>();

	public final Collection<String> getRegionNames() {
		return nameToTextureRegions.keySet();
	}

    public final void loadTexturesIntoAtlas(final String texturesPath, final String atlasName) {
        final String tempPath = "temp/";
		String inputDir = PathUtils.internalToAbsolutePath(texturesPath);
		String outputDir = Gdx.files.local(tempPath).file().getAbsolutePath();
		String name = texturesPath.replace("/", "_");
		TexturePacker.process(inputDir, outputDir, name);
        loadAtlas(Gdx.files.local(tempPath + name + ".atlas"), atlasName);
    }

    public final void loadTextures(final FileHandle fileHandle, final String namespace) {
        final String[] textureExtensions = { "png", "jpg" };
		if (fileHandle.isDirectory()) {
			for (FileHandle child : fileHandle.list()) {
                loadTextures(child, namespace);
            }
		} else if (ArrayUtils.contains(textureExtensions, fileHandle.extension())) {
			Texture texture = new Texture(fileHandle);
			texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			TextureRegion region = new TextureRegion(texture);
			addRegion(namespace, fileHandle.nameWithoutExtension(), region);
		}
	}

    public final TextureAtlas getAtlas(final String name) {
        if (!nameToAtlas.containsKey(name)) {
            throw new IllegalArgumentException("Could not get texture atlas " + name
                    + " because it does not exist");
		}
        return nameToAtlas.get(name);
    }

    public final void loadAtlas(final FileHandle fileHandle, final String name) {
        TextureAtlas atlas = new TextureAtlas(fileHandle);
        nameToAtlas.put(name, atlas);
        for (AtlasRegion atlasRegion : atlas.getRegions()) {
            addRegion(name, atlasRegion.name, atlasRegion);
        }
	}

	public final PolygonRegion getPolygonRegion(final String name) {
		TextureRegion textureRegion = getTextureRegion(name);
        return TextureUtils.createPolygonRegion(textureRegion);
	}

	public final PolygonRegion getPolygonRegion(final String name, final float[] vertices) {
		TextureRegion textureRegion = getTextureRegion(name);
		return TextureUtils.createPolygonRegion(textureRegion, vertices);
	}

	public final TextureRegion getTextureRegion(final String name) {
		if (!nameToTextureRegions.containsKey(name)) {
			throw new IllegalArgumentException("Could not get texture region " + name + " because it does not exist");
		}
		return nameToTextureRegions.get(name);
	}

	public void addRegion(final TextureRegion region) {
		textureRegions.add(region);
	}

	public void addRegion(final String namespace, final String localName, final TextureRegion region) {
		nameToTextureRegions.put(namespace + "/" + localName, region);
	}

	public final void dispose() {
        for (TextureAtlas atlas : nameToAtlas.values()) {
            atlas.dispose();
		}
		dispose(textureRegions);
		dispose(nameToTextureRegions.values());
	}

	private final void dispose(final Collection<TextureRegion> textureRegions) {
		for (TextureRegion region : textureRegions) {
			region.getTexture().dispose();
		}
	}

}