package fr.lewon.ff.bot.scripts

import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues
import fr.lewon.ff.bot.util.logs.LogItem

abstract class BotScriptBuilder(val name: String, val isDev: Boolean = false) {

    abstract fun getParameters(): List<BotParameter<*>>

    abstract fun getDefaultStats(): List<BotScriptStat>

    abstract fun getDescription(): String

    fun buildScript(): BotScript {
        return object : BotScript() {
            override fun execute(
                logItem: LogItem,
                parameterValues: ParameterValues,
                statValues: HashMap<BotScriptStat, String>,
            ) {
                statValues.putAll(getDefaultStats().associateWith { it.defaultValue })
                doExecuteScript(logItem, parameterValues, statValues)
            }
        }
    }

    protected abstract fun doExecuteScript(
        logItem: LogItem,
        parameterValues: ParameterValues,
        statValues: HashMap<BotScriptStat, String>,
    )

}