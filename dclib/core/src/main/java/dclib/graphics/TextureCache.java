package dclib.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import dclib.geometry.PolygonFactory;
import dclib.util.PathUtils;

public final class TextureCache {
	
	private final List<TextureRegion> textureRegions = new ArrayList<TextureRegion>();
	private final Map<String, TextureRegion> nameToTextureRegions = new HashMap<String, TextureRegion>();
	private final List<TextureAtlas> textureAtlases = new ArrayList<TextureAtlas>();
	
	public final Collection<String> getRegionNames() {
		return nameToTextureRegions.keySet();
	}
	public final void addTexturesAsAtlas(final String texturesPath, final String namespace) {
		final String tempPath = "temp/";
		String inputDir = PathUtils.internalToAbsolutePath(texturesPath);
		String outputDir = Gdx.files.local(tempPath).file().getAbsolutePath();
		String name = texturesPath.replace("/", "_");
		TexturePacker.process(inputDir, outputDir, name);
		addTextureAtlas(Gdx.files.local(tempPath + name + ".atlas"), namespace);
	}
	
	public final void addTextures(final FileHandle fileHandle, final String namespace) {
		final String[] textureExtensions = { "png", "jpg" };
		if (fileHandle.isDirectory()) {
			for (FileHandle child : fileHandle.list()) {
				addTextures(child, namespace);
			}
		} else if (ArrayUtils.contains(textureExtensions, fileHandle.extension())) {
			Texture texture = new Texture(fileHandle);
			texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
			TextureRegion region = new TextureRegion(texture);
			addRegion(namespace, fileHandle.nameWithoutExtension(), region);
		}
	}
	
	public final void addTextureAtlas(final FileHandle fileHandle, final String namespace) {
		TextureAtlas atlas = new TextureAtlas(fileHandle);
		textureAtlases.add(atlas);
		for (AtlasRegion atlasRegion : atlas.getRegions()) {
			addRegion(namespace, atlasRegion.name, atlasRegion);
		}
	}
	
	public void addRegion(final TextureRegion region) {
		textureRegions.add(region);
	}
	
	public void addRegion(final String namespace, final String localName, final TextureRegion region) {
		nameToTextureRegions.put(namespace + "/" + localName, region);
	}
	
	public final PolygonRegion getPolygonRegion(final String name) {
		TextureRegion textureRegion = getTextureRegion(name);
		float[] vertices = PolygonFactory.createRectangleVertices(textureRegion.getRegionWidth(), 
				textureRegion.getRegionHeight());
		return getPolygonRegion(name, vertices);
	}
	
	public final PolygonRegion getPolygonRegion(final String name, final float[] vertices) {
		TextureRegion textureRegion = getTextureRegion(name);
		return RegionFactory.createPolygonRegion(textureRegion, vertices);
	}
	
	public final TextureRegion getTextureRegion(final String name) {
		if (!nameToTextureRegions.containsKey(name)) {
			throw new IllegalArgumentException("Could not get texture region " + name + " because it does not exist");
		}
		return nameToTextureRegions.get(name);
	}
	
	public final void dispose() {
		for (TextureAtlas atlas : textureAtlases) {
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