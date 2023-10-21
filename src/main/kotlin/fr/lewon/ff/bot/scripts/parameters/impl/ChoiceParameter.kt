package fr.lewon.ff.bot.scripts.parameters.impl

import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues

class ChoiceParameter<T>(
    key: String,
    description: String,
    defaultValue: T,
    parametersGroup: Int? = null,
    displayCondition: (parameterValues: ParameterValues) -> Boolean = { true },
    val getAvailableValues: (parameterValues: ParameterValues) -> List<T>,
    private val itemValueToString: (value: T) -> String,
    private val stringToItemValue: (str: String) -> T,
) : BotParameter<T>(key, description, defaultValue, parametersGroup, displayCondition) {

    override fun stringToValue(rawValue: String): T = stringToItemValue(rawValue)

    override fun valueToString(value: T): String = itemValueToString(value)
}