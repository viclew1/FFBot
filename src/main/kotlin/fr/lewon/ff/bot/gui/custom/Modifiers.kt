package fr.lewon.ff.bot.gui.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.dp
import fr.lewon.ff.bot.gui.util.AppColors
import java.awt.Cursor

@Composable
fun Modifier.defaultHoverManager(
    isHovered: MutableState<Boolean> = remember { mutableStateOf(false) },
    onHover: (Offset) -> Unit = {},
    onExit: (Offset) -> Unit = {},
    key: Any = "",
): Modifier = composed {
    this.onMouseMove(key) { location, newIsHovered ->
        if (isHovered.value != newIsHovered) {
            isHovered.value = newIsHovered
            if (newIsHovered) {
                onHover(location)
            } else {
                onExit(location)
            }
        }
    }
}

@Composable
fun Modifier.onMouseMove(
    key: Any = "",
    callback: (location: Offset, isHovered: Boolean) -> Unit,
): Modifier = composed {
    this.pointerInput(key) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent(PointerEventPass.Initial)
                val pointerInputChange = event.changes[0]
                val isOutsideRelease = event.type == PointerEventType.Release &&
                    pointerInputChange.isOutOfBounds(size, Size.Zero)
                val isHovered = event.type != PointerEventType.Exit && !isOutsideRelease
                callback(pointerInputChange.position, isHovered)
            }
        }
    }
}

fun Modifier.handPointerIcon(): Modifier = pointerHoverIcon(PointerIcon(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))

fun Modifier.grayBoxStyle(): Modifier = background(AppColors.DARK_BG_COLOR).border(BorderStroke(1.dp, Color.Gray))

private val darkGrayBoxBackgroundColor = Color(0xFF151515)
private val darkGrayBoxBorder = BorderStroke(2.dp, Color(0xFF101010))

fun Modifier.darkGrayBoxStyle(): Modifier = background(darkGrayBoxBackgroundColor).border(darkGrayBoxBorder)

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onTabChangeFocus(focusManager: FocusManager) = this.onPreviewKeyEvent {
    if (it.key == Key.Tab && it.type == KeyEventType.KeyDown) {
        val direction = if (it.isShiftPressed) FocusDirection.Up else FocusDirection.Down
        focusManager.moveFocus(direction)
        true
    } else {
        false
    }
}

@Composable
fun Modifier.onFocusHighlight(): Modifier {
    val backgroundColor = remember { mutableStateOf(Color.Transparent) }
    return this.onFocusChanged {
        backgroundColor.value = if (it.isFocused) Color.White else Color.Transparent
    }.border(BorderStroke(1.dp, backgroundColor.value))
}