package fr.lewon.ff.bot.gui.main.scripts

import androidx.compose.runtime.mutableStateOf
import fr.lewon.ff.bot.gui.ComposeUIUtil
import fr.lewon.ff.bot.gui.main.scripts.parameters.ScriptParametersUIUtil
import fr.lewon.ff.bot.scripts.BotScriptBuilder
import fr.lewon.ff.bot.util.LockUtils.executeSyncOperation
import fr.lewon.ff.bot.util.script.ScriptEndType
import fr.lewon.ff.bot.util.script.ScriptRunner
import fr.lewon.ff.bot.util.script.ScriptRunnerListener
import java.util.concurrent.locks.ReentrantLock

object ScriptsUiUtil : ComposeUIUtil(), ScriptRunnerListener {

    private val lock = ReentrantLock()
    private val uiState = mutableStateOf(ScriptsUiState())

    init {
        ScriptRunner.addListener(this)
    }

    fun getUiStateValue() = lock.executeSyncOperation {
        uiState.value
    }

    fun isScriptStarted(): Boolean = lock.executeSyncOperation {
        uiState.value.scriptStarted
    }

    fun isToggleScriptButtonEnabled(): Boolean = lock.executeSyncOperation {
        uiState.value.isToggleScriptButtonEnabled
    }

    fun toggleScript() {
        lock.executeSyncOperation {
            uiState.value = uiState.value.copy(isToggleScriptButtonEnabled = false)
        }
        Thread {
            val isStarted = isScriptStarted()
            if (isStarted) {
                ScriptRunner.stopScript()
            } else {
                val scriptBuilder = getUiStateValue().currentScriptBuilder
                val parameterValues = ScriptParametersUIUtil.getParameterValues(scriptBuilder)
                ScriptRunner.runScript(scriptBuilder, parameterValues)
            }
        }.start()
    }

    fun updateCurrentScriptBuilder(scriptBuilder: BotScriptBuilder) = lock.executeSyncOperation {
        uiState.value = uiState.value.copy(currentScriptBuilder = scriptBuilder)
    }

    override fun onScriptEnd(endType: ScriptEndType) = lock.executeSyncOperation {
        uiState.value = uiState.value.copy(scriptStarted = false, isToggleScriptButtonEnabled = true)
    }

    override fun onScriptStart(script: ScriptRunner.RunningScript) {
        uiState.value = uiState.value.copy(scriptStarted = true, isToggleScriptButtonEnabled = true)
    }

}