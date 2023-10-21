package fr.lewon.ff.bot.gui.main.settings

import fr.lewon.ff.bot.model.config.GlobalConfig
import fr.lewon.ff.bot.util.filemanagers.impl.GlobalConfigManager

data class SettingsUIState(
    val globalConfig: GlobalConfig = GlobalConfigManager.readConfig(),
)