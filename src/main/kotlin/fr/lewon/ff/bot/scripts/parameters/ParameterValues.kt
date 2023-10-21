package fr.lewon.ff.bot.scripts.parameters

class ParameterValues : HashMap<String, String>() {

    fun <T> getParamValue(parameter: BotParameter<T>): T {
        return this[parameter.key]?.let {
            parameter.stringToValue(it)
        } ?: parameter.defaultValue
    }

    fun <T> updateParamValue(parameter: BotParameter<T>, parameterValue: T) {
        this[parameter.key] = parameter.valueToString(parameterValue)
    }

    fun deepCopy(): ParameterValues {
        return ParameterValues().also { it.putAll(this) }
    }

}