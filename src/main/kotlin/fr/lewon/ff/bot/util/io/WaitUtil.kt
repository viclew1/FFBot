package fr.lewon.ff.bot.util.io

import fr.lewon.ff.bot.util.filemanagers.impl.GlobalConfigManager
import fr.lewon.ff.bot.util.game.GameColors
import fr.lewon.ff.bot.util.geometry.PointAbsolute
import fr.lewon.ff.bot.util.geometry.RectangleAbsolute

object WaitUtil {

    const val DEFAULT_TIMEOUT_MILLIS = 25 * 1000

    fun sleep(time: Long) {
        Thread.sleep(time)
    }

    fun sleep(time: Int) {
        sleep(time.toLong())
    }

    fun waitUntilCharacterAvailable(timeOutMillis: Int = DEFAULT_TIMEOUT_MILLIS): Boolean {
        val sprintSpellPosition = GlobalConfigManager.readConfig().sprintSpellPosition.toPointAbsolute()
        val bounds = RectangleAbsolute.build(
            topLeft = PointAbsolute(sprintSpellPosition.x - 15, sprintSpellPosition.y - 15),
            bottomRight = PointAbsolute(sprintSpellPosition.x + 15, sprintSpellPosition.y + 15)
        )
        return waitUntil(timeOutMillis) {
            ScreenUtil.colorCount(bounds, GameColors.SprintMinColor, GameColors.SprintMaxColor) != 0
        }
    }

    fun waitUntil(timeOutMillis: Int = DEFAULT_TIMEOUT_MILLIS, condition: () -> Boolean): Boolean {
        val start = System.currentTimeMillis()
        while (System.currentTimeMillis() - start < timeOutMillis) {
            if (condition()) {
                return true
            }
            sleep(100)
        }
        return false
    }

}