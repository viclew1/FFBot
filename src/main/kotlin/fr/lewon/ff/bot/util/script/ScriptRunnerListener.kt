package fr.lewon.ff.bot.util.script

interface ScriptRunnerListener {

    fun onScriptEnd(endType: ScriptEndType)

    fun onScriptStart(script: ScriptRunner.RunningScript)

}
