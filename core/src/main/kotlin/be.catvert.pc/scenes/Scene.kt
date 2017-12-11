package be.catvert.pc.scenes

import aurelienribon.tweenengine.TweenAccessor
import be.catvert.pc.Log
import be.catvert.pc.PCGame
import be.catvert.pc.components.RenderableComponent
import be.catvert.pc.containers.GameObjectContainer
import be.catvert.pc.utility.*
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.Disposable
import imgui.ImGui
import ktx.app.use

/**
 * Classe abstraite permettant l'implémentation d'une scène
 */
abstract class Scene(protected var background: Background) : Renderable, Updeatable, Resizable, Disposable {
    protected open val camera = OrthographicCamera().apply { setToOrtho(false); }

    val backgroundColors = Triple(0f, 0f, 0f)

    protected open var gameObjectContainer: GameObjectContainer = object : GameObjectContainer() {}

    protected var isUIHover = false
        private set

    var alpha = 1f
        set(value) {
            field = value
            gameObjectContainer.getGameObjectsData().forEach {
                it.getStates().forEach {
                    it.getComponent<RenderableComponent>()?.alpha = value
                }
            }
        }

    protected open fun postBatchRender() {}

    fun calcIsUIHover() {
        isUIHover = false

        isUIHover = imgui.findHoveredWindow(ImGui.mousePos) != null || ImGui.isAnyItemHovered
    }

    override fun render(batch: Batch) {
        batch.projectionMatrix = PCGame.defaultProjection
        batch.use {
            background.render(it)

            it.projectionMatrix = camera.combined
            gameObjectContainer.render(it)
        }

        postBatchRender()
    }

    override fun update() {
        gameObjectContainer.update()
    }

    override fun resize(size: Size) {
        camera.setToOrtho(false, size.width.toFloat(), size.height.toFloat())
    }

    override fun dispose() {}
}

class SceneTweenAccessor : TweenAccessor<Scene> {
    enum class SceneTween(val tweenType: Int) {
        ALPHA(0);

        companion object {
            fun fromType(tweenType: Int) = values().firstOrNull { it.tweenType == tweenType }
        }
    }

    override fun setValues(scene: Scene, tweenType: Int, newValues: FloatArray) {
        when (SceneTween.fromType(tweenType)) {
            SceneTween.ALPHA -> {
                scene.alpha = newValues[0]
            }
            else -> Log.error { "Tween inconnu : $tweenType" }
        }
    }

    override fun getValues(scene: Scene, tweenType: Int, returnValues: FloatArray): Int {
        when (SceneTween.fromType(tweenType)) {
            SceneTween.ALPHA -> {
                returnValues[0] = scene.alpha; return 1
            }
            else -> Log.error { "Tween inconnu : $tweenType" }
        }

        return -1
    }
}