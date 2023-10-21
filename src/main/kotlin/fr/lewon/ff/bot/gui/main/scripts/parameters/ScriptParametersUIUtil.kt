package fr.lewon.ff.bot.gui.main.scripts.parameters

import androidx.compose.runtime.mutableStateOf
import fr.lewon.ff.bot.gui.ComposeUIUtil
import fr.lewon.ff.bot.scripts.BotScriptBuilder
import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues
import fr.lewon.ff.bot.util.LockUtils.executeSyncOperation
import java.util.concurrent.locks.ReentrantLock

object ScriptParametersUIUtil : ComposeUIUtil() {

    private val lock = ReentrantLock()
    private val uiState = mutableStateOf(ScriptParametersUiState())

    fun getUiStateValue() = lock.executeSyncOperation {
        uiState.value
    }

    fun getParameterValues(scriptBuilder: BotScriptBuilder) = lock.executeSyncOperation {
        val uiStateValue = getUiStateValue()
        val parameterValuesByScript = uiStateValue.parameterValuesByScript
        val parameterValues = uiStateValue.parameterValuesByScript[scriptBuilder]
            ?: ParameterValues()
        uiState.value = uiStateValue.copy(
            parameterValuesByScript = parameterValuesByScript.plus(scriptBuilder to parameterValues)
        )
        parameterValues.deepCopy()
    }

    fun <T> updateParameterValue(
        scriptBuilder: BotScriptBuilder,
        parameter: BotParameter<T>,
        value: T,
    ) = lock.executeSyncOperation {
        val uiStateValue = getUiStateValue()
        val parameterValues = getParameterValues(scriptBuilder)
        parameterValues.updateParamValue(parameter, value)
        uiState.value = uiStateValue.copy(
            parameterValuesByScript = uiStateValue.parameterValuesByScript.plus(
                scriptBuilder to parameterValues
            )
        )
    }
}