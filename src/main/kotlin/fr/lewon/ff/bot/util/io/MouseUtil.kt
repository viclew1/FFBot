package fr.lewon.ff.bot.util.io

import fr.lewon.ff.bot.util.GameInfo
import fr.lewon.ff.bot.util.geometry.PointRelative
import java.awt.Robot
import java.awt.event.InputEvent

object MouseUtil {

    private val robot = Robot()

    fun leftClick(pointRelative: PointRelative) {
        val pointAbsolute = pointRelative.toPointAbsolute()
        val gameBounds = GameInfo.gameBounds
        val x = gameBounds.x + pointAbsolute.x
        val y = gameBounds.y + pointAbsolute.y
        robot.mouseMove(x, y)
        Thread.sleep((35..70).random().toLong())
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        Thread.sleep((35..70).random().toLong())
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    }

}
