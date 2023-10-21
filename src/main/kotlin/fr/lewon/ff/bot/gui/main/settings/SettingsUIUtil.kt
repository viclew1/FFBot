package fr.lewon.ff.bot.gui.main.settings

import androidx.compose.runtime.mutableStateOf
import fr.lewon.ff.bot.gui.ComposeUIUtil
import fr.lewon.ff.bot.model.config.GlobalConfig
import fr.lewon.ff.bot.util.filemanagers.impl.GlobalConfigManager

object SettingsUIUtil : ComposeUIUtil() {

    val SETTINGS_UI_STATE = mutableStateOf(SettingsUIState())

    fun updateGlobalConfig(update: (GlobalConfig) -> Unit) {
        GlobalConfigManager.editConfig(update)
        SETTINGS_UI_STATE.value = SETTINGS_UI_STATE.value.copy(globalConfig = GlobalConfigManager.readConfig())
    }

}