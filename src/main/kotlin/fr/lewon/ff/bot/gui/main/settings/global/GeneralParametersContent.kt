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
        HorizontalSeparator("Left spell position")
        PositionInput(
            point = globalConfig.leftSpellPosition,
            onXUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.leftSpellPosition = Point(it, config.leftSpellPosition.y)
                }
            },
            onYUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.leftSpellPosition = Point(config.leftSpellPosition.x, it)
                }
            }
        )
        HorizontalSeparator("Top spell position")
        PositionInput(
            point = globalConfig.topSpellPosition,
            onXUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.topSpellPosition = Point(it, config.topSpellPosition.y)
                }
            },
            onYUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.topSpellPosition = Point(config.topSpellPosition.x, it)
                }
            }
        )
        HorizontalSeparator("Right spell position")
        PositionInput(
            point = globalConfig.rightSpellPosition,
            onXUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.rightSpellPosition = Point(it, config.rightSpellPosition.y)
                }
            },
            onYUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.rightSpellPosition = Point(config.rightSpellPosition.x, it)
                }
            }
        )
        HorizontalSeparator("Bottom spell position")
        PositionInput(
            point = globalConfig.bottomSpellPosition,
            onXUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.bottomSpellPosition = Point(it, config.bottomSpellPosition.y)
                }
            },
            onYUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.bottomSpellPosition = Point(config.bottomSpellPosition.x, it)
                }
            }
        )
        HorizontalSeparator("Craft position")
        PositionInput(
            point = globalConfig.craftPosition,
            onXUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.craftPosition = Point(it, config.craftPosition.y)
                }
            },
            onYUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.craftPosition = Point(config.craftPosition.x, it)
                }
            }
        )
        HorizontalSeparator("Sprint spell position")
        PositionInput(
            point = globalConfig.sprintSpellPosition,
            onXUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.sprintSpellPosition = Point(it, config.sprintSpellPosition.y)
                }
            },
            onYUpdate = {
                SettingsUIUtil.updateGlobalConfig { config ->
                    config.sprintSpellPosition = Point(config.sprintSpellPosition.x, it)
                }
            }
        )
    }
}

@Composable
private fun PositionInput(point: Point, onXUpdate: (Int) -> Unit, onYUpdate: (Int) -> Unit) {
    Row {
        Row(Modifier.fillMaxWidth().weight(1f)) {
            Text("X : ", modifier = Modifier.align(Alignment.CenterVertically))
            IntInput(point.x, onXUpdate)
        }
        Row(Modifier.fillMaxWidth().weight(1f)) {
            Text("Y : ", modifier = Modifier.align(Alignment.CenterVertically))
            IntInput(point.y, onYUpdate)
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