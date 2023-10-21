package fr.lewon.ff.bot.scripts.tasks

import fr.lewon.ff.bot.scripts.tasks.exceptions.BotTaskFatalException
import fr.lewon.ff.bot.util.logs.LogItem

abstract class BooleanBotTask : BotTask<Boolean>() {

    private var error: Throwable? = null

    override fun shouldClearSubLogItems(result: Boolean): Boolean {
        return result
    }

    override fun execute(logItem: LogItem): Boolean {
        error = null
        return try {
            doExecute(logItem)
        } catch (e: Throwable) {
            when (e) {
                is BotTaskFatalException,
                is InterruptedException,
                is IllegalMonitorStateException ->
                    throw e
                else -> {
                    e.printStackTrace()
                    error = e
                    false
                }
            }
        }
    }

    protected abstract fun doExecute(logItem: LogItem): Boolean

    override fun onSucceeded(value: Boolean): String {
        return if (value) {
            super.onSucceeded(value)
        } else {
            error?.let { super.onFailed(it) } ?: "KO"
        }
    }

}