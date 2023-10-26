package fr.lewon.ff.bot.util.io

import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.platform.win32.WinUser.INPUT
import fr.lewon.ff.bot.util.geometry.PointRelative
import java.awt.Robot
import java.awt.Toolkit
import java.awt.event.InputEvent

object MouseUtil {

    private const val MOUSEEVENTF_MOVE = 0x0001
    private const val MOUSEEVENTF_LEFTUP = 0x0004
    private const val MOUSEEVENTF_LEFTDOWN = 0x0020
    private const val MOUSEEVENTF_ABSOLUTE = 0x8000

    private val robot = Robot()

    fun leftClick(pointRelative: PointRelative) {
        val point = pointRelative.toPointAbsolute().toPoint()
        Thread.sleep(60L)
        robot.mouseMove(point.x, point.y)
        Thread.sleep(60L)
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
        Thread.sleep(60L)
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
    }

    fun leftClickExperimental(position: PointRelative) {
        val point = position.toPointAbsolute().toPoint()
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val input = INPUT()
        val inputArray = input.toArray(3) as Array<INPUT>
        inputArray[0].also {
            it.type = WinDef.DWORD(INPUT.INPUT_MOUSE.toLong())
            it.input.mi.dwFlags = WinDef.DWORD(MOUSEEVENTF_LEFTUP.toLong())
        }
        inputArray[1].also {
            it.type = WinDef.DWORD(INPUT.INPUT_MOUSE.toLong())
            it.input.mi.dx = WinDef.LONG((point.x * 65535 / screenSize.width).toLong())
            it.input.mi.dy = WinDef.LONG((point.y * 65535 / screenSize.height).toLong())
            it.input.mi.dwFlags =
                WinDef.DWORD((MOUSEEVENTF_LEFTDOWN or MOUSEEVENTF_MOVE or MOUSEEVENTF_ABSOLUTE).toLong())
        }
        inputArray[2].also {
            it.type = WinDef.DWORD(INPUT.INPUT_MOUSE.toLong())
            it.input.mi.dx = WinDef.LONG((point.x * 65535 / screenSize.width).toLong())
            it.input.mi.dy = WinDef.LONG((point.y * 65535 / screenSize.height).toLong())
            it.input.mi.dwFlags =
                WinDef.DWORD((MOUSEEVENTF_LEFTUP or MOUSEEVENTF_MOVE or MOUSEEVENTF_ABSOLUTE).toLong())
        }
        val result = User32.INSTANCE.SendInput(WinDef.DWORD(inputArray.size.toLong()), inputArray, input.size())
        if (result.toInt() == 0) {
            error("Failed to click, error code : ${Kernel32.INSTANCE.GetLastError()}")
        }
    }

}
