package fr.lewon.ff.bot.scripts.parameters.impl

import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues

class IntParameter(
    key: String,
    description: String,
    defaultValue: Int,
    parametersGroup: Int? = null,
    displayCondition: (parameterValues: ParameterValues) -> Boolean = { true },
) : BotParameter<Int>(key, description, defaultValue, parametersGroup, displayCondition) {

    override fun stringToValue(rawValue: String): Int = rawValue.toInt()

    override fun valueToString(value: Int): String = value.toString()
}