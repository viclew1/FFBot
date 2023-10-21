package fr.lewon.ff.bot.gui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import fr.lewon.ff.bot.gui.custom.defaultHoverManager
import fr.lewon.ff.bot.gui.custom.handPointerIcon
import fr.lewon.ff.bot.gui.main.status.StatusBarContent
import fr.lewon.ff.bot.gui.util.AppColors

@Composable
fun MainContent() {
    val modifier = Modifier.fillMaxSize()
        .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {}
    PressDraggable(modifier) {
        TooltipManageable {
            Column(Modifier.fillMaxSize()) {
                MainNavigationRail()
                Box(Modifier.fillMaxSize()) {
                    Row(Modifier.padding(bottom = 30.dp)) {
                        MainContentUIUtil.mainContentUIState.value.currentAppContent.content()
                    }
                    Row(Modifier.align(Alignment.BottomCenter)) {
                        StatusBarContent()
                    }
                }
            }
        }
    }
}

@Composable
private fun MainNavigationRail() {
    Row(modifier = Modifier.fillMaxWidth().height(64.dp)) {
        NavigationRail(modifier = Modifier.fillMaxSize().weight(1f)) {
            Row {
                for (appContent in MainAppContent.entries) {
                    Divider(Modifier.fillMaxHeight(0.8f).width(2.dp).align(Alignment.CenterVertically))
                    val uiState = MainContentUIUtil.mainContentUIState
                    val selected = uiState.value.currentAppContent == appContent
                    NavigationButton(
                        selected = selected,
                        onClick = { uiState.value = uiState.value.copy(currentAppContent = appContent) },
                        title = appContent.title,
                        iconPainter = appContent.uiResource.imagePainter
                    )
                }
                Spacer(Modifier.fillMaxWidth().weight(1f))
            }
        }
    }
}

@Composable
private fun NavigationButton(
    selected: Boolean,
    onClick: () -> Unit,
    title: String,
    iconPainter: Painter,
    imageModifier: Modifier = Modifier
) {
    val isHovered = remember { mutableStateOf(false) }
    NavigationRailItem(selected,
        onClick = onClick,
        modifier = Modifier.defaultHoverManager(isHovered).handPointerIcon().fillMaxHeight()
            .padding(vertical = 3.dp),
        icon = {
            val backgroundColor = Color.Transparent
            TooltipTarget(title, modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.width(64.dp).fillMaxHeight()) {
                    SelectedIndicator(Modifier.align(Alignment.CenterHorizontally), selected, isHovered.value)
                    Image(iconPainter, "", imageModifier.fillMaxSize().background(backgroundColor))
                }
            }
        }
    )
}

@Composable
private fun SelectedIndicator(modifier: Modifier, selected: Boolean, hovered: Boolean) {
    Box(modifier.fillMaxWidth().height(4.dp)) {
        AnimatedIndicator(Modifier.align(Alignment.Center), selected, 0.7f) { it / 2 }
        AnimatedIndicator(Modifier.align(Alignment.Center), hovered, 0.4f)
    }
}

@Composable
private fun AnimatedIndicator(
    modifier: Modifier,
    visible: Boolean,
    maxWidthRatio: Float,
    initialWidth: (fullHeight: Int) -> Int = { 0 }
) {
    val shape = CutCornerShape(bottomStartPercent = 100, bottomEndPercent = 100)
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = expandHorizontally(expandFrom = Alignment.CenterHorizontally, initialWidth = initialWidth),
        exit = shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally)
    ) {
        Box(Modifier.fillMaxHeight().fillMaxWidth(maxWidthRatio).clip(shape).background(AppColors.primaryColor))
    }
}
