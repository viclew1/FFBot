package fr.lewon.ff.bot.util

import fr.lewon.ff.bot.util.logs.VldbLogger
import java.awt.Rectangle
import java.util.concurrent.locks.ReentrantLock

object GameInfo {

    val lock = ReentrantLock()
    val logger = VldbLogger()
    var gameBounds = Rectangle()
    var pid = -1L

}