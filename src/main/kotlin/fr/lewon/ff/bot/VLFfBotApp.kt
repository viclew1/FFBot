package fr.lewon.ff.bot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toPainter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import fr.lewon.ff.bot.gui.ComposeUIUtil
import fr.lewon.ff.bot.gui.custom.handPointerIcon
import fr.lewon.ff.bot.gui.main.MainContent
import fr.lewon.ff.bot.gui.main.MainContentUIUtil
import fr.lewon.ff.bot.gui.util.AppInfo
import fr.lewon.ff.bot.gui.util.UiResource
import fr.lewon.ff.bot.gui.util.getScaledImage
import fr.lewon.ff.bot.util.filemanagers.ToInitManager
import org.reflections.Reflections
import java.awt.Dimension
import kotlin.system.exitProcess

class VLFfBotApp

lateinit var windowState: WindowState

fun main() = application {
    windowState = rememberWindowState(size = DpSize(350.dp, 900.dp))
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        undecorated = true,
        resizable = false,
        icon = UiResource.TASKBAR_LOGO.imageData.getScaledImage(32).toPainter(),
        alwaysOnTop = true
    ) {
        window.minimumSize = Dimension(350, 800)
        window.maximumSize = Dimension(350, 10000)
        AppInfo.mainTheme(Typography(FontFamily.SansSerif)) {
            Scaffold(
                topBar = { WindowDraggableArea { topBar() } },
                bottomBar = { },
                content = { MainContent() },
                drawerGesturesEnabled = true,
            )
        }
    }
    LaunchedEffect(Unit) {
        initFileManagers()
        initUIUtils()
    }
}

private fun initFileManagers() {
    val managers = Reflections(ToInitManager::class.java.packageName)
        .getSubTypesOf(ToInitManager::class.java)
        .filter { !it.kotlin.isAbstract }
        .mapNotNull { it.kotlin.objectInstance ?: it.getConstructor().newInstance() }

    val initializedManagers = ArrayList<ToInitManager>()
    managers.forEach { initManager(it, initializedManagers) }
}

private fun initManager(
    manager: ToInitManager,
    initializedManagers: ArrayList<ToInitManager>
) {
    manager.getNeededManagers().forEach {
        initManager(it, initializedManagers)
    }
    if (!initializedManagers.contains(manager)) {
        manager.initManager()
        initializedManagers.add(manager)
    }
}

private fun initUIUtils() {
    Reflections(ComposeUIUtil::class.java.packageName)
        .getSubTypesOf(ComposeUIUtil::class.java)
        .filter { !it.kotlin.isAbstract }
        .mapNotNull { it.kotlin.objectInstance ?: it.getConstructor().newInstance() }
        .forEach { it.init() }
}

@Composable
private fun topBar() {
    TopAppBar(
        title = { "${AppInfo.APP_NAME} - ${MainContentUIUtil.mainContentUIState.value.currentAppContent.title}" },
        navigationIcon = { Image(UiResource.GLOBAL_LOGO.imageData.getScaledImage(64).toPainter(), "") },
        actions = {
            appBarButton("—") { windowState.isMinimized = !windowState.isMinimized }
            appBarButton("X") { exitProcess(0) }
        }
    )
}

@Composable
private fun appBarButton(text: String, action: () -> Unit) {
    Button(
        action,
        Modifier.width(30.dp).fillMaxHeight(),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
        elevation = null
    ) {
        Row(Modifier.handPointerIcon().fillMaxSize()) {
            Text(text, Modifier.align(Alignment.CenterVertically).fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }
}