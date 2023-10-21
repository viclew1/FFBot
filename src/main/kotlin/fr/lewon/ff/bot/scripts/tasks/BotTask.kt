package fr.lewon.ff.bot.scripts.tasks

import fr.lewon.ff.bot.util.GameInfo
import fr.lewon.ff.bot.util.logs.LogItem

abstract class BotTask<T> {

    protected abstract fun execute(logItem: LogItem): T

    protected open fun onFailed(error: Throwable): String {
        return "KO - [${if (error is InterruptedException) "EXECUTION STOPPED" else error.localizedMessage}]"
    }

    protected open fun onSucceeded(value: T): String {
        return "OK"
    }

    protected open fun shouldClearSubLogItems(result: T): Boolean {
        return true
    }

    protected abstract fun onStarted(): String

    fun run(parentLogItem: LogItem): T {
        val logItem = GameInfo.logger.addSubLog(onStarted(), parentLogItem)
        try {
            val result = execute(logItem)
            GameInfo.logger.closeLog(onSucceeded(result), logItem, shouldClearSubLogItems(result))
            return result
        } catch (e: Throwable) {
            GameInfo.logger.closeLog(onFailed(e), logItem)
            throw e
        }
    }
}