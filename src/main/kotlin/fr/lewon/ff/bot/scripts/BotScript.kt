package fr.lewon.ff.bot.scripts

import fr.lewon.ff.bot.scripts.parameters.ParameterValues
import fr.lewon.ff.bot.util.logs.LogItem

abstract class BotScript {

    abstract fun execute(
        logItem: LogItem,
        parameterValues: ParameterValues,
        statValues: HashMap<BotScriptStat, String>,
    )

}