package fr.lewon.ff.bot.gui.main.scripts.parameters

import fr.lewon.ff.bot.scripts.BotScriptBuilder

import fr.lewon.ff.bot.scripts.parameters.ParameterValues

data class ScriptParametersUiState(
    val parameterValuesByScript: Map<BotScriptBuilder, ParameterValues> = emptyMap(),
)