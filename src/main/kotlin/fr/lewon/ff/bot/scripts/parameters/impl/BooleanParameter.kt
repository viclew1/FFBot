package fr.lewon.ff.bot.scripts.parameters.impl

import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues

class BooleanParameter(
    key: String,
    description: String,
    defaultValue: Boolean,
    parametersGroup: Int? = null,
    displayCondition: (parameterValues: ParameterValues) -> Boolean = { true },
) : BotParameter<Boolean>(key, description, defaultValue, parametersGroup, displayCondition) {

    override fun stringToValue(rawValue: String): Boolean = rawValue.toBoolean()

    override fun valueToString(value: Boolean): String = value.toString()
}