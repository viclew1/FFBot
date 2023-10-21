package fr.lewon.ff.bot.util.filemanagers

interface ToInitManager {

    fun initManager()

    fun getNeededManagers(): List<ToInitManager>

}