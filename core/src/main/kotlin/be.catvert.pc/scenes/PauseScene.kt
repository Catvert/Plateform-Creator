package be.catvert.pc.scenes

import be.catvert.pc.PCGame
import be.catvert.pc.utility.Size
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import ktx.actors.onClick
import ktx.actors.plus
import ktx.assets.loadOnDemand
import ktx.vis.window

class PauseScene(private val gameScene: GameScene) : Scene() {
    private val logo = PCGame.generateLogo(this)

    init {
        stage + window("Menu principal") {
            verticalGroup {
                space(10f)

                textButton("Reprendre") {
                    onClick {
                        PCGame.setScene(gameScene)
                    }
                }
                textButton("Quitter le niveau") {
                    onClick {
                        PCGame.setScene(MainMenuScene())
                    }
                }
            }

            setPosition(Gdx.graphics.width / 2f - width / 2f, Gdx.graphics.height / 2f - height / 2f)
        }
        backgroundTexture = PCGame.assetManager.loadOnDemand<Texture>("assets/game/mainmenu.png").asset
    }

    override fun update() {
        super.update()

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            PCGame.setScene(gameScene)
    }

    override fun resize(size: Size) {
        super.resize(size)
        logo.rectangle.set(PCGame.getLogoRect())
    }
}