package fr.lewon.ff.bot.gui.main.settings

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.lewon.ff.bot.gui.custom.TitleText
import fr.lewon.ff.bot.gui.custom.darkGrayBoxStyle
import fr.lewon.ff.bot.gui.custom.grayBoxStyle
import fr.lewon.ff.bot.gui.main.settings.global.GlobalConfigContent

@Composable
fun SettingsContent() {
    Box(Modifier.fillMaxSize().padding(5.dp)) {
        val scrollState = rememberScrollState()
        Column(Modifier.verticalScroll(scrollState).padding(end = 14.dp)) {
            Column(Modifier.fillMaxWidth().grayBoxStyle()) {
                Row(Modifier.fillMaxWidth().height(40.dp).darkGrayBoxStyle()) {
                    TitleText(
                        "Global configuration",
                        Modifier.padding(start = 10.dp).align(Alignment.CenterVertically)
                    )
                }
                Row(Modifier.padding(5.dp)) {
                    GlobalConfigContent()
                }
            }
        }
        VerticalScrollbar(
            modifier = Modifier.fillMaxHeight().width(8.dp).align(Alignment.CenterEnd),
            adapter = rememberScrollbarAdapter(scrollState),
        )
    }
}