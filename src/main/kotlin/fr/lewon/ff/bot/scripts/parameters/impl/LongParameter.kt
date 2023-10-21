package fr.lewon.ff.bot.scripts.parameters.impl

import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues

class LongParameter(
    key: String,
    description: String,
    defaultValue: Long,
    parametersGroup: Int? = null,
    displayCondition: (parameterValues: ParameterValues) -> Boolean = { true },
) : BotParameter<Long>(key, description, defaultValue, parametersGroup, displayCondition) {

    override fun stringToValue(rawValue: String): Long = rawValue.toLong()

    override fun valueToString(value: Long): String = value.toString()
}