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
import java.util.ArrayList
import java.util.HashMap

class TextureCache {
    private val textureRegions = ArrayList<TextureRegion>()
    private val nameToTextureRegions = HashMap<String, TextureRegion>()
    private val nameToAtlas = HashMap<String, TextureAtlas>()
    private val convexHulls = HashMap<String, FloatArray>()

    val regionNames get() = nameToTextureRegions.keys

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

    fun getPolygonRegion(name: String, vertices: FloatArray): PolygonRegion {
        val textureRegion = getTextureRegion(name)
        return TextureUtils.createPolygonRegion(textureRegion, vertices)
    }

    fun getTextureRegion(name: String): TextureRegion {
        if (!nameToTextureRegions.containsKey(name)) {
            throw IllegalArgumentException("Could not get texture region $name because it does not exist")
        }
        return nameToTextureRegions[name]!!
    }

    fun addRegion(region: TextureRegion) {
        textureRegions.add(region)
    }

    fun addRegion(namespace: String, localName: String, region: TextureRegion) {
        nameToTextureRegions.put(namespace + "/" + localName, region)
    }

    fun createHull(regionName: String): HullData {
        val region = getPolygonRegion(regionName)
        if (!convexHulls.containsKey(regionName)) {
            val convexHull = TextureUtils.createConvexHull(region.region)
            convexHulls.put(regionName, convexHull)
        }
        return HullData(convexHulls[regionName]!!, region)
    }

    fun createHull(regionName: String, size: Vector2): HullData {
        val hullData = createHull(regionName)
        PolygonUtils.setSize(hullData.hull, size)
        return hullData
    }

    fun dispose() {
        for (atlas in nameToAtlas.values) {
            atlas.dispose()
        }
        dispose(textureRegions)
        dispose(nameToTextureRegions.values)
    }

    private fun dispose(textureRegions: Collection<TextureRegion>) {
        for (region in textureRegions) {
            region.texture.dispose()
        }
    }
}