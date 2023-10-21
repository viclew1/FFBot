package fr.lewon.ff.bot.util.logs

interface VldbLoggerListener {

    fun onLogUpdated(logger: VldbLogger, logItem: LogItem)

}