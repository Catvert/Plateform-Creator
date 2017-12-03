package be.catvert.pc.scenes

import be.catvert.pc.PCGame
import be.catvert.pc.containers.Level
import be.catvert.pc.utility.Size
import com.badlogic.gdx.Gdx
import ktx.actors.onClick
import ktx.actors.plus
import ktx.assets.toLocalFile
import ktx.vis.window

/**
 * Scène de fin de niveau, appelé lorsque le joueur meurt ou fini le niveau
 * @param game L'objet du jeu
 * @param levelFile Le fichier utilisé pour charger le niveau
 * @param levelSuccess Permet de spécifier si oui ou non le joueur a réussi le niveau
 */
class EndLevelScene(private val levelPath: String) : Scene(PCGame.mainBackground) {
    private val logo = PCGame.generateLogo(gameObjectContainer)

    init {
        stage + window("Fin de partie") {
            verticalGroup {
                space(10f)

                textButton("Recommencer") {
                    addListener(onClick {
                        val level = Level.loadFromFile(levelPath.toLocalFile().parent())
                        if (level != null)
                            SceneManager.loadScene(GameScene(level))
                    })
                }
                textButton("Quitter") {
                    addListener(onClick {
                        SceneManager.loadScene(MainMenuScene(), false)
                    })
                }
            }
            setPosition(Gdx.graphics.width / 2f - width / 2f, Gdx.graphics.height / 2f - height / 2f)
        }
    }


    override fun resize(size: Size) {
        super.resize(size)
        logo.box.set(PCGame.getLogoRect())
    }
}