package fr.lewon.ff.bot.scripts.impl.dev

import fr.lewon.ff.bot.scripts.BotScriptBuilder
import fr.lewon.ff.bot.scripts.BotScriptStat
import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues
import fr.lewon.ff.bot.util.logs.LogItem

object TestScriptBuilder : BotScriptBuilder("Test", true) {

    override fun getParameters(): List<BotParameter<*>> {
        return listOf()
    }

    override fun getDefaultStats(): List<BotScriptStat> {
        return listOf()
    }

    override fun getDescription(): String {
        return "Test script for development only"
    }

    override fun doExecuteScript(
        logItem: LogItem,
        parameterValues: ParameterValues,
        statValues: HashMap<BotScriptStat, String>,
    ) {
    }

}