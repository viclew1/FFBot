package fr.lewon.ff.bot.util.io

import fr.lewon.ff.bot.util.geometry.PointRelative
import java.awt.Robot
import java.awt.event.InputEvent

object MouseUtil {

    private val robot = Robot()

    fun leftClick(pointRelative: PointRelative) {
        val point = pointRelative.toPointAbsolute().toPoint()
        robot.mouseMove(point.x, point.y)
        Thread.sleep((60..70).random().toLong())
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        Thread.sleep((60..70).random().toLong())
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    }

}
