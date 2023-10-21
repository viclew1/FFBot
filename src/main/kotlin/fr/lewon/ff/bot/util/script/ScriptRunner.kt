package fr.lewon.ff.bot.util.script

import fr.lewon.ff.bot.gui.util.SoundType
import fr.lewon.ff.bot.model.spells.SpellPosition
import fr.lewon.ff.bot.scripts.BotScriptBuilder
import fr.lewon.ff.bot.scripts.BotScriptStat
import fr.lewon.ff.bot.scripts.parameters.ParameterValues
import fr.lewon.ff.bot.util.GameInfo
import fr.lewon.ff.bot.util.Listenable
import fr.lewon.ff.bot.util.io.ScreenUtil
import fr.lewon.ff.bot.util.io.toPointAbsolute
import fr.lewon.ff.bot.util.logs.LogItem
import org.jetbrains.skia.Color
import java.io.File
import javax.imageio.ImageIO

object ScriptRunner : Listenable<ScriptRunnerListener>() {

    private var runningScript: RunningScript? = null

    @Synchronized
    fun runScript(scriptBuilder: BotScriptBuilder, parameterValues: ParameterValues) {
        if (isScriptRunning()) {
            error("Cannot run script, there is already one running")
        }
        val logger = GameInfo.logger
        val logItem = logger.log("Executing Ff script : [${scriptBuilder.name}]")
        val stats = HashMap<BotScriptStat, String>()
        val thread = Thread {
            try {
                GameInfo.gameBounds = ScreenUtil.getGameBounds()
                ScreenUtil.takeCapture().also {
                    val left = SpellPosition.Left.getPosition().toPointAbsolute()
                    it.setRGB(left.x, left.y, Color.RED)
                    val r = SpellPosition.Right.getPosition().toPointAbsolute()
                    it.setRGB(r.x, r.y, Color.RED)
                    val t = SpellPosition.Top.getPosition().toPointAbsolute()
                    it.setRGB(t.x, t.y, Color.RED)
                    val b = SpellPosition.Bottom.getPosition().toPointAbsolute()
                    it.setRGB(b.x, b.y, Color.RED)
                    ImageIO.write(it, "png", File("C:/Dev/printscreen.png"))
                }
                val script = scriptBuilder.buildScript()
                script.execute(logItem, parameterValues, stats)
                onScriptOk(logItem)
            } catch (e: Throwable) {
                when (e) {
                    is InterruptedException,
                    is IllegalMonitorStateException -> onScriptCanceled(logItem)
                    else -> onScriptKo(e, logItem)
                }
            }
        }
        val runningScript = RunningScript(scriptBuilder, thread, stats)
        this.runningScript = runningScript
        getListeners().forEach { it.onScriptStart(runningScript) }
        println("[Running script : ${scriptBuilder.name}]")
        thread.start()
    }

    fun isScriptRunning(): Boolean {
        return runningScript?.thread?.isAlive == true
    }

    fun getRunningScript(): RunningScript? {
        return runningScript?.takeIf { it.thread.isAlive }
    }

    @Synchronized
    fun stopScript() {
        println("[Stopping script]")
        runningScript?.thread?.interrupt()
    }

    private fun onScriptKo(t: Throwable, logItem: LogItem) {
        GameInfo.logger.closeLog("Execution KO - ${t.localizedMessage}", logItem)
        SoundType.FAILED.playSound()
        t.printStackTrace()
        onScriptEnd(ScriptEndType.FAIL)
    }

    private fun onScriptCanceled(logItem: LogItem) {
        GameInfo.logger.closeLog("Execution canceled", logItem)
        onScriptEnd(ScriptEndType.CANCEL)
    }

    private fun onScriptOk(logItem: LogItem) {
        GameInfo.logger.closeLog("Execution OK", logItem)
        SoundType.SUCCEEDED.playSound()
        onScriptEnd(ScriptEndType.SUCCESS)
    }

    private fun onScriptEnd(endType: ScriptEndType) {
        println("[Script finished : ${endType.name}]")
        getListeners().forEach { it.onScriptEnd(endType) }
    }

    class RunningScript(
        val scriptBuilder: BotScriptBuilder,
        val thread: Thread,
        val stats: Map<BotScriptStat, String>,
        val startTime: Long = System.currentTimeMillis(),
    )
}