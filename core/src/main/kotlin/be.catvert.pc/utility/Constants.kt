package be.catvert.pc.utility

import be.catvert.pc.serialization.SerializationFactory
import ktx.assets.toLocalFile

/**
 * Permet d'accéder aux constantes du jeu
 */
object Constants {
    const val gameTitle = "Platform-Creator"
    const val gameVersion = 2.0f

    const val maxEntitySize = 10000
    const val maxLayer = 100
    const val defaultLayer = 5

    const val minMatrixSize = 10
    const val matrixCellSize = 300

    const val viewportRatioWidth = 1920f
    const val viewportRatioHeight = 1080f

    const val defaultWidgetsWidth = 125f

    const val assetsDir = "assets/"

    const val physicsEpsilon = 0.2f
    const val physicsDeltaSpeed = 60f
    const val defaultGravitySpeed = 15

    val serializationType = SerializationFactory.MapperType.JSON

    private val assetsDirPath = "assets/".toLocalFile()

    const val configPath = assetsDir + "config.json"

    val bundlesDirPath = assetsDirPath.child("i18n_bundles")

    val uiDirPath = assetsDirPath.child("ui/")

    val fontDirPath = assetsDirPath.child("fonts/")
    val imguiFontPath = fontDirPath.child("imgui.ttf")
    val editorFontPath = fontDirPath.child("editorFont.fnt")

    val keysConfigPath = assetsDirPath.child("keysConfig.json")

    val packsDirPath = assetsDirPath.child("packs")
    val packsKenneyDirPath = packsDirPath.child("kenney")
    val packsSMCDirPath = packsDirPath.child("smc")

    val gameDirPath = assetsDirPath.child("game")

    val texturesDirPath = assetsDirPath.child("textures")
    val soundsDirPath = assetsDirPath.child("sounds")
    val musicsDirPath = assetsDirPath.child("musics")
    val backgroundsDirPath = assetsDirPath.child("backgrounds")
    val levelDirPath = assetsDirPath.child("levels")

    val gameBackgroundMenuPath = gameDirPath.child("mainmenu.png")
    val gameLogoPath = gameDirPath.child("logo.png")
    val menuMusicPath = gameDirPath.child("main_music.ogg")

    val prefabExtension = "prefab"
    val groupExtension = "group"
    val levelExtension = "pclvl"



    val levelTextureExtension = arrayOf("jpg", "png")
    val levelPackExtension = arrayOf("atlas")
    val levelSoundExtension = arrayOf("mp3", "wav", "ogg")
    val levelScriptExtension = arrayOf("js")

    val exportLevelExtension = arrayOf("zip")

    val levelCustomBackgroundFile = "background.png"
    val levelDataFile = "data.$levelExtension"
    val levelPreviewFile = "preview.png"
    val levelCustomMusicFile = "music.mp3"

    val defaultSoundPath = assetsDirPath.child("game/nosound.wav")
}