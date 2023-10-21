package fr.lewon.ff.bot.gui.main.settings.global

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.lewon.ff.bot.gui.custom.HorizontalSeparator
import fr.lewon.ff.bot.gui.custom.IntegerTextField
import fr.lewon.ff.bot.gui.main.settings.ConfigLine
import fr.lewon.ff.bot.gui.main.settings.SettingsUIUtil
import fr.lewon.ff.bot.model.config.GlobalConfig
import java.awt.Point

@Composable
fun GeneralParametersContent() {
    val globalConfig = SettingsUIUtil.SETTINGS_UI_STATE.value.globalConfig
    Column {
        ConfigLine("General", "", true) {}
        HorizontalSeparator("Is game framed")
        Switch(globalConfig.isFramed, {
            SettingsUIUtil.updateGlobalConfig { config -> config.isFramed = it }
        })
        HorizontalSeparator("Left spell 1 position")
        PositionInput(globalConfig.leftSpell1Position) { globalConfig, point ->
            globalConfig.leftSpell1Position = point
        }
        HorizontalSeparator("Top spell 1 position")
        PositionInput(globalConfig.topSpell1Position) { globalConfig, point ->
            globalConfig.topSpell1Position = point
        }
        HorizontalSeparator("Right spell 1 position")
        PositionInput(globalConfig.rightSpell1Position) { globalConfig, point ->
            globalConfig.rightSpell1Position = point
        }
        HorizontalSeparator("Bottom spell 1 position")
        PositionInput(globalConfig.bottomSpell1Position) { globalConfig, point ->
            globalConfig.bottomSpell1Position = point
        }
        HorizontalSeparator("Left spell 2 position")
        PositionInput(globalConfig.leftSpell2Position) { globalConfig, point ->
            globalConfig.leftSpell2Position = point
        }
        HorizontalSeparator("Top spell 2 position")
        PositionInput(globalConfig.topSpell2Position) { globalConfig, point ->
            globalConfig.topSpell2Position = point
        }
        HorizontalSeparator("Right spell 2 position")
        PositionInput(globalConfig.rightSpell2Position) { globalConfig, point ->
            globalConfig.rightSpell2Position = point
        }
        HorizontalSeparator("Bottom spell 2 position")
        PositionInput(globalConfig.bottomSpell2Position) { globalConfig, point ->
            globalConfig.bottomSpell2Position = point
        }
        HorizontalSeparator("Craft position")
        PositionInput(globalConfig.craftPosition) { globalConfig, point ->
            globalConfig.craftPosition = point
        }
        HorizontalSeparator("Sprint spell position")
        PositionInput(globalConfig.sprintSpellPosition) { globalConfig, point ->
            globalConfig.sprintSpellPosition = point
        }
    }
}

@Composable
private fun PositionInput(point: Point, updatePoint: (GlobalConfig, Point) -> Unit) {
    Row {
        Row(Modifier.fillMaxWidth().weight(1f)) {
            Text("X : ", modifier = Modifier.align(Alignment.CenterVertically))
            IntInput(point.x) {
                SettingsUIUtil.updateGlobalConfig { config -> updatePoint(config, Point(it, point.y)) }
            }
        }
        Row(Modifier.fillMaxWidth().weight(1f)) {
            Text("Y : ", modifier = Modifier.align(Alignment.CenterVertically))
            IntInput(point.y) {
                SettingsUIUtil.updateGlobalConfig { config -> updatePoint(config, Point(point.x, it)) }
            }
        }
    }
}

@Composable
private fun IntInput(value: Int, onUpdate: (Int) -> Unit) {
    IntegerTextField(
        value.toString(),
        {
            it.toIntOrNull()?.let { newValue ->
                onUpdate(newValue)
            }
        })
}