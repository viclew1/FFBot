package fr.lewon.ff.bot.gui.util

import androidx.compose.ui.graphics.toPainter

enum class UiResource(path: String) {

    GLOBAL_LOGO("/icon/global_logo.png"),
    TASKBAR_LOGO("/icon/taskbar_logo.png"),
    SCRIPT_LOGO("/icon/script_logo.png"),
    SETTINGS_LOGO("/icon/settings_logo.png"),
    GITHUB("/icon/github.png"),
    DISCORD("/icon/discord.png"),
    ;

    val imageData = javaClass.getResourceAsStream(path)?.readAllBytes()
        ?: error("Couldn't find image [$path]")
    val image = imageData.getBufferedImage()
    val imagePainter = image.toPainter()
}
