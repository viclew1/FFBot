package fr.lewon.ff.bot.gui.main

import androidx.compose.runtime.Composable
import fr.lewon.ff.bot.gui.main.scripts.ScriptsContent
import fr.lewon.ff.bot.gui.main.settings.SettingsContent
import fr.lewon.ff.bot.gui.util.UiResource

enum class MainAppContent(val title: String, val uiResource: UiResource, val content: @Composable () -> Unit) {
    SCRIPTS("Scripts", UiResource.SCRIPT_LOGO, { ScriptsContent() }),
    SETTINGS("Settings", UiResource.SETTINGS_LOGO, { SettingsContent() }),
}