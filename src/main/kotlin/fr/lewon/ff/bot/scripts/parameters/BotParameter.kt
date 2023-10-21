package fr.lewon.ff.bot.scripts.parameters

abstract class BotParameter<T>(
    val key: String,
    val description: String,
    val defaultValue: T,
    val parametersGroup: Int? = null,
    val displayCondition: (parameterValues: ParameterValues) -> Boolean = { true },
) {

    abstract fun stringToValue(rawValue: String): T

    abstract fun valueToString(value: T): String
}