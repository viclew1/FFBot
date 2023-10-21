package fr.lewon.ff.bot.scripts.parameters.impl

import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues

class StringParameter(
    key: String,
    description: String,
    defaultValue: String,
    parametersGroup: Int? = null,
    displayCondition: (parameterValues: ParameterValues) -> Boolean = { true },
) : BotParameter<String>(key, description, defaultValue, parametersGroup, displayCondition) {

    override fun stringToValue(rawValue: String): String = rawValue

    override fun valueToString(value: String): String = value
}