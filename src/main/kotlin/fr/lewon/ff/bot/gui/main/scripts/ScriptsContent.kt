package fr.lewon.ff.bot.gui.main.scripts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import fr.lewon.ff.bot.gui.main.scripts.parameters.ScriptParametersContent
import fr.lewon.ff.bot.gui.main.scripts.selector.ScriptSelectorContent

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScriptsContent() {
    Column(Modifier.onPreviewKeyEvent { keyEvent ->
        if (keyEvent.isCtrlPressed && keyEvent.key in listOf(Key.Enter, Key.NumPadEnter)) {
            if (!ScriptsUiUtil.isScriptStarted() && ScriptsUiUtil.isToggleScriptButtonEnabled()) {
                ScriptsUiUtil.toggleScript()
                true
            } else false
        } else false
    }) {
        ScriptSelectorContent()
        val parameters = ScriptsUiUtil.getUiStateValue().currentScriptBuilder.getParameters()
        Column(Modifier.fillMaxHeight().weight(1f)) {
            AnimatedVisibility(
                parameters.isNotEmpty(),
                enter = expandVertically(expandFrom = Alignment.Top),
                exit = shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
                ScriptParametersContent()
            }
            Spacer(Modifier.fillMaxHeight().weight(1f))
        }
    }
}