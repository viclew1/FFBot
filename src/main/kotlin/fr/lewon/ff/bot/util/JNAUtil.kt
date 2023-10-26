package fr.lewon.ff.bot.util

import com.sun.jna.platform.win32.GDI32Util
import com.sun.jna.platform.win32.User32
import com.sun.jna.platform.win32.WinDef
import com.sun.jna.ptr.IntByReference
import java.awt.Point
import java.awt.image.BufferedImage
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO

object JNAUtil {

    private const val tasklistCommand = "cmd.exe /c tasklist /fo csv /nh | findstr \"ffxiv_dx11.exe\""
    private val pidLineRegex = Regex("\".*?\",\"([0-9]+)\"")

    fun getPid(): Long? {
        val tasklistProcessBuilder = ProcessBuilder(tasklistCommand.split(" "))
        val process = tasklistProcessBuilder.start()
        if (process.waitFor(5L, TimeUnit.SECONDS)) {
            val pidLine = BufferedReader(InputStreamReader(process.inputStream)).readLines().firstOrNull() ?: ""
            return pidLineRegex.find(pidLine)?.destructured?.component1()?.toLong()
        }
        return null
    }

    fun findByPID(pid: Long): WinDef.HWND? {
        val foundHandlesWithSizes = HashMap<WinDef.HWND, Int>()
        User32.INSTANCE.EnumWindows({ handle, _ ->
            val pidRef = IntByReference()
            User32.INSTANCE.GetWindowThreadProcessId(handle, pidRef)
            if (pidRef.value.toLong() == pid) {
                val rect = WinDef.RECT()
                User32.INSTANCE.GetClientRect(handle, rect)
                val width = rect.right - rect.left
                val height = rect.bottom - rect.top
                val isHandleValid = width > 10 && height > 10 && User32.INSTANCE.IsWindowVisible(handle)
                if (isHandleValid) {
                    foundHandlesWithSizes[handle] = width * height
                }
            }
            true
        }, null)
        return foundHandlesWithSizes.entries.maxByOrNull { it.value }?.key
    }

    fun takeCapture(pid: Long): BufferedImage {
        val handle = findByPID(pid) ?: error("Can't take capture, no handle for PID : $pid")
        return GDI32Util.getScreenshot(handle)
    }

    fun getGamePosition(pid: Long): Point {
        val rect = WinDef.RECT()
        User32.INSTANCE.GetWindowRect(findByPID(pid), rect)
        return Point(rect.left + 8, rect.top + 8)
    }

}

fun main() {
    
}