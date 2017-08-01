package dclib.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureWrap
import com.badlogic.gdx.graphics.g2d.PolygonRegion
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.tools.texturepacker.TexturePacker
import dclib.geometry.PolygonUtils
import dclib.system.io.PathUtils
import org.apache.commons.lang3.ArrayUtils
import java.util.HashMap

// TODO: Refactor texture loading out of texture caching
class TextureCache(private val convertRegionToVertices: (TextureRegion) -> FloatArray) {
    private val regionDatas = mutableListOf<RegionData>()
    // TODO: Move to another class? Doesn't fit with the other member variables
    private val nameToAtlas = HashMap<String, TextureAtlas>()

    fun loadTexturesIntoAtlas(texturesPath: String, atlasName: String) {
        val tempPath = "temp/"
        val inputDir = PathUtils.internalToAbsolutePath(texturesPath)
        val outputDir = Gdx.files.local(tempPath).file().absolutePath
        val name = texturesPath.replace("/", "_")
        TexturePacker.process(inputDir, outputDir, name)
        loadAtlas(Gdx.files.local(tempPath + name + ".atlas"), atlasName)
    }

    fun loadTextures(fileHandle: FileHandle, namespace: String) {
        val textureExtensions = arrayOf("png", "jpg")
        if (fileHandle.isDirectory) {
            for (child in fileHandle.list()) {
                loadTextures(child, namespace)
            }
        } else if (ArrayUtils.contains(textureExtensions, fileHandle.extension())) {
            val texture = Texture(fileHandle)
            texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat)
            val region = TextureRegion(texture)
            addRegion(namespace, fileHandle.nameWithoutExtension(), region)
        }
    }

    fun getAtlas(name: String): TextureAtlas {
        if (!nameToAtlas.containsKey(name)) {
            throw IllegalArgumentException("Could not get texture atlas " + name
                    + " because it does not exist")
        }
        return nameToAtlas[name]!!
    }

    fun loadAtlas(fileHandle: FileHandle, name: String) {
        val atlas = TextureAtlas(fileHandle)
        nameToAtlas.put(name, atlas)
        for (atlasRegion in atlas.regions) {
            addRegion(name, atlasRegion.name, atlasRegion)
        }
    }

    fun getPolygonRegion(name: String): PolygonRegion {
        val textureRegion = getTextureRegion(name)
        return TextureUtils.createPolygonRegion(textureRegion)
    }

    fun getTextureRegion(name: String): TextureRegion {
        val regionData = regionDatas.singleOrNull { it.name == name }
                ?: throw IllegalArgumentException("Texture data $name does not exist")
        return regionData.region
    }

    fun getHull(region: TextureRegion, size: Vector2): FloatArray {
        if (!regionDatas.any { equals(it.region, region) }) {
            addRegion("", "", region)
        }
        val hull = regionDatas.single { equals(it.region, region) }.hull.copyOf()
        PolygonUtils.setSize(hull, size)
        return hull
    }

    fun getHull(regionName: String, size: Vector2): FloatArray {
        val hull = regionDatas.single { it.name == regionName }.hull.copyOf()
        PolygonUtils.setSize(hull, size)
        return hull
    }

    fun addRegion(namespace: String, localName: String, region: TextureRegion) {
        val name = "$namespace/$localName"
        val existingRegionData = regionDatas.singleOrNull { equals(it.region, region) }
        if (existingRegionData == null) {
            val regionData = RegionData(name, region, convertRegionToVertices(region))
            regionDatas.add(regionData)
        } else {
            existingRegionData.name = name
        }
    }

    fun dispose() {
        for (atlas in nameToAtlas.values) {
            atlas.dispose()
        }
        val textures = regionDatas.map { it.region.texture }.distinct()
        for (texture in textures) {
            texture.dispose()
        }
    }

    private fun equals(r1: TextureRegion, r2: TextureRegion): Boolean {
        return r1.texture == r2.texture && r1.u == r2.u && r1.u2 == r2.u2 && r1.v == r2.v && r1.v2 == r2.v2
                && r1.regionWidth == r2.regionWidth && r1.regionHeight == r2.regionHeight
    }

    private data class RegionData(var name: String, val region: TextureRegion, val hull: FloatArray)
}