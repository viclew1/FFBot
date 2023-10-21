package fr.lewon.ff.bot.gui.custom

import androidx.compose.ui.input.key.KeyEvent

class KeyHandler(val checkKey: (KeyEvent) -> Boolean, val handleKeyEvent: () -> Unit)