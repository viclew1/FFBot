package fr.lewon.ff.bot.gui.main.status

data class StatusBarUIState(
    val oldMessages: List<String> = emptyList(),
    val currentStatus: String = ""
)