package fr.lewon.ff.bot.util

import java.io.File

object FilesUtil {

    fun getConfigDirectory(): String {
        val configDirPath = System.getProperty("user.home") + "/.FFXIV_Bot"
        mkdirIfNeeded(configDirPath)
        return configDirPath
    }

    private fun mkdirIfNeeded(directoryPath: String) {
        val dirFile = File(directoryPath)
        if (!dirFile.exists() || !dirFile.isDirectory) {
            dirFile.mkdir()
        }
    }

}