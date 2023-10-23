package fr.lewon.ff.bot.util.io

import fr.lewon.ff.bot.util.GameInfo
import fr.lewon.ff.bot.util.filemanagers.impl.GlobalConfigManager
import fr.lewon.ff.bot.util.geometry.PointAbsolute
import fr.lewon.ff.bot.util.geometry.PointRelative
import fr.lewon.ff.bot.util.geometry.RectangleAbsolute
import fr.lewon.ff.bot.util.geometry.RectangleRelative
import java.awt.*
import java.awt.image.BufferedImage

object ScreenUtil {

    fun getGameBounds(): Rectangle {
        val isFramed = GlobalConfigManager.readConfig().isFramed
        val screenRect = Rectangle(Toolkit.getDefaultToolkit().screenSize)
        if (!isFramed) {
            return screenRect
        }
        val marginTop = ((23f / 1080f) * screenRect.height).toInt()
        val marginBottom = ((41f / 1080f) * screenRect.height).toInt()
        return Rectangle(0, marginTop, screenRect.width, screenRect.height - marginBottom - marginTop)
    }

    fun takeCapture(): BufferedImage {
        return Robot().createScreenCapture(GameInfo.gameBounds)
    }

    fun getPixelColor(point: PointAbsolute, fullScreenshot: BufferedImage): Color {
        return getPixelColor(point.toPoint(), fullScreenshot)
    }

    fun getPixelColor(point: Point, fullScreenshot: BufferedImage): Color {
        return Color(fullScreenshot.getRGB(point.x, point.y))
    }

    fun getPixelColor(
        point: PointRelative,
        fullScreenshot: BufferedImage = takeCapture()
    ): Color {
        return getPixelColor(point.toPointAbsolute(), fullScreenshot)
    }

    fun isBetween(point: PointRelative, min: Color, max: Color): Boolean {
        return isBetween(getPixelColor(point), min, max)
    }

    fun isBetween(color: Color, min: Color, max: Color): Boolean {
        return color.red in min.red..max.red &&
            color.green in min.green..max.green &&
            color.blue in min.blue..max.blue
    }

    fun colorCount(
        bounds: RectangleRelative,
        min: Color,
        max: Color,
        fullScreenshot: BufferedImage = takeCapture()
    ): Int {
        return colorCount(bounds.toRectangleAbsolute(), min, max, fullScreenshot)
    }

    fun colorCount(
        bounds: RectangleAbsolute,
        min: Color,
        max: Color,
        fullScreenshot: BufferedImage = takeCapture()
    ): Int {
        return colorCount(bounds, fullScreenshot) { isBetween(it, min, max) }
    }

    private fun colorCount(
        bounds: RectangleAbsolute,
        fullScreenshot: BufferedImage = takeCapture(),
        acceptColorCondition: (Color) -> Boolean
    ): Int {
        return colorCount(bounds.toRectangle(), fullScreenshot, acceptColorCondition)
    }

    private fun colorCount(
        bounds: Rectangle,
        fullScreenshot: BufferedImage = takeCapture(),
        acceptColorCondition: (Color) -> Boolean
    ): Int {
        var cpt = 0
        val minX = 0
        val minY = 0
        val maxX = fullScreenshot.width
        val maxY = fullScreenshot.height
        for (x in bounds.x..<bounds.x + bounds.width) {
            for (y in bounds.y..<bounds.y + bounds.height) {
                if (x in minX..<maxX && y in minY..<maxY) {
                    val color = getPixelColor(Point(x, y), fullScreenshot)
                    if (acceptColorCondition(color)) {
                        cpt++
                    }
                }
            }
        }
        return cpt
    }
}