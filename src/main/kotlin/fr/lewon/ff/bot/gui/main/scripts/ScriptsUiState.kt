package fr.lewon.ff.bot.gui.main.scripts

import fr.lewon.ff.bot.scripts.BotScriptBuilder
import fr.lewon.ff.bot.scripts.BotScriptBuilders

data class ScriptsUiState(
    val currentScriptBuilder: BotScriptBuilder = BotScriptBuilders.entries.first().builder,
    val scriptStarted: Boolean = false,
    val isToggleScriptButtonEnabled: Boolean = true
)