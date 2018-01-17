package be.catvert.pc.components.logics

import be.catvert.pc.GameObject
import be.catvert.pc.actions.Action
import be.catvert.pc.actions.EmptyAction
import be.catvert.pc.components.Component
import be.catvert.pc.containers.Level
import be.catvert.pc.scenes.EditorScene
import be.catvert.pc.utility.*
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.fasterxml.jackson.annotation.JsonCreator


/**
 * Component permettant d'effectuer une action quand l'utilisateur appuie sur une touche
 */
class InputComponent(var inputs: ArrayList<InputData>) : Component(), Updeatable, CustomEditorImpl {
    constructor(vararg inputs: InputData) : this(arrayListOf(*inputs))
    @JsonCreator private constructor() : this(arrayListOf())

    data class InputData(var key: Int = Input.Keys.UNKNOWN, @ExposeEditor var justPressed: Boolean = false, @ExposeEditor var action: Action = EmptyAction()) : CustomEditorImpl {
        override fun insertImgui(label: String, gameObject: GameObject, level: Level, editorSceneUI: EditorScene.EditorSceneUI) {
            ImguiHelper.gdxKey(::key)
        }
    }

    override fun update() {
        inputs.forEach {
            if (it.justPressed) {
                if (Gdx.input.isKeyJustPressed(it.key))
                    it.action(gameObject)
            } else {
                if (Gdx.input.isKeyPressed(it.key))
                    it.action(gameObject)
            }
        }
    }

    override fun insertImgui(label: String, gameObject: GameObject, level: Level, editorSceneUI: EditorScene.EditorSceneUI) {
        ImguiHelper.addImguiWidgetsArray("inputs", inputs, { item -> Input.Keys.toString(item.key) }, { InputData() }, gameObject, level, editorSceneUI)
    }

    override fun toString(): String {
        val stringBuilder =  StringBuilder()

        inputs.forEach {
            stringBuilder.appendln("<-->")
            stringBuilder.appendln("key : ${Input.Keys.toString(it.key)}")
            stringBuilder.appendln("action : ${it.action}")
            stringBuilder.appendln("just pressed : ${it.justPressed}")
            stringBuilder.appendln("pressed : ${if(it.justPressed) Gdx.input.isKeyJustPressed(it.key) else Gdx.input.isKeyPressed(it.key)}")
            stringBuilder.appendln("<-->")
        }

        return stringBuilder.toString()
    }
}