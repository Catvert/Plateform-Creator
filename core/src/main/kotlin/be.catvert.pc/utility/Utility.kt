package be.catvert.pc.utility

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import ktx.assets.Asset
import ktx.assets.loadOnDemand
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import kotlin.math.roundToInt
import kotlin.reflect.KClass
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaConstructor


fun Batch.draw(textureRegion: TextureRegion, rect: Rect, flipX: Boolean = false, flipY: Boolean = false, rotation: Float = 0f) {
    if (flipX && !textureRegion.isFlipX || !flipX && textureRegion.isFlipX) {
        textureRegion.flip(true, false)
    }
    if (flipY && !textureRegion.isFlipY || !flipY && textureRegion.isFlipY) {
        textureRegion.flip(false, true)
    }

    this.draw(textureRegion, rect.x.roundToInt().toFloat(), rect.y.roundToInt().toFloat(), rect.width / 2f, rect.height / 2f, rect.width.toFloat(), rect.height.toFloat(), 1f, 1f, rotation)
}

fun ShapeRenderer.rect(rect: Rect) = this.rect(rect.x, rect.y, rect.width.toFloat(), rect.height.toFloat())

fun Vector2.toPoint() = Point(this.x, this.y)

fun Vector3.toPoint() = Point(this.x, this.y)

fun Shape2D.contains(point: Point) = this.contains(point.x, point.y)

inline fun <reified T : Any> AssetManager.loadOnDemand(file: FileHandle): Asset<T> = this.loadOnDemand(file.path())

fun FileHandle.toFileWrapper() = FileWrapper(this)

fun Texture.toAtlasRegion() = TextureAtlas.AtlasRegion(this, 0, 0, width, height)

fun Float.equalsEpsilon(v: Float, epsilon: Float) = Math.abs(this - v) < epsilon

fun Batch.withColor(color: Color, block: Batch.() -> Unit) {
    val oldColor = this.color.cpy()
    this.color = color
    this.block()
    this.color = oldColor
}

fun ShapeRenderer.withColor(color: Color, block: ShapeRenderer.() -> Unit) {
    val oldColor = this.color.cpy()
    this.color = color
    this.block()
    this.color = oldColor
}

inline fun <reified T : Any> Any?.cast(): T? = this as? T

object Utility {
    fun getFilesRecursivly(dir: FileHandle, vararg fileExt: String = arrayOf()): List<FileHandle> {
        val files = mutableListOf<FileHandle>()

        dir.list().forEach {
            if (it.isDirectory)
                files += getFilesRecursivly(it, *fileExt)
            else {
                if (fileExt.isEmpty() || fileExt.contains(it.extension()))
                    files += it
            }
        }
        return files
    }

    fun getDeltaTime() = Math.min(Gdx.graphics.deltaTime, 0.1f)
}

object ReflectionUtility {
    inline fun <reified T : Any> hasNoArgConstructor(klass: KClass<out T>) = findNoArgConstructor(klass) != null

    inline fun <reified T : Any> findNoArgConstructor(klass: KClass<out T>): Constructor<T>? {
        klass.constructors.forEach {
            if (it.parameters.isEmpty())
                return it.javaConstructor.apply { it.isAccessible = true }
        }
        return null
    }


    fun getAllFieldsOf(type: Class<*>): List<Field> {
        val fields = mutableListOf<Field>()

        var i: Class<*>? = type

        while (i != null && i != Any::class.java) {
            fields.addAll(i.declaredFields)
            i = i.superclass
        }

        return fields
    }

    fun simpleNameOf(instance: Any) = instance.javaClass.kotlin.simpleName ?: "Nom introuvable"
}