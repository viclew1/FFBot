package fr.lewon.ff.bot.util.io

import fr.lewon.ff.bot.util.GameInfo
import fr.lewon.ff.bot.util.geometry.PointAbsolute
import fr.lewon.ff.bot.util.geometry.PointRelative
import fr.lewon.ff.bot.util.geometry.RectangleAbsolute
import fr.lewon.ff.bot.util.geometry.RectangleRelative
import java.awt.Point
import java.awt.Rectangle
import kotlin.math.roundToInt

object ConverterUtil {

    private const val PRECISION = 10000.0f

    fun toPoint(point: PointAbsolute): Point {
        return Point(
            point.x + GameInfo.gameBounds.x,
            point.y + GameInfo.gameBounds.y
        )
    }

    fun toRectangle(rectangleAbsolute: RectangleAbsolute): Rectangle {
        val topLeft = rectangleAbsolute.getTopLeft().toPoint()
        val bottomRight = rectangleAbsolute.getBottomRight().toPoint()
        return Rectangle(
            topLeft.x,
            topLeft.y,
            bottomRight.x - topLeft.x,
            bottomRight.y - topLeft.y
        )
    }

    fun toPointRelative(point: PointAbsolute): PointRelative {
        val x = (point.x - GameInfo.gameBounds.x).toFloat() / GameInfo.gameBounds.width.toFloat()
        val y = (point.y - GameInfo.gameBounds.y).toFloat() / GameInfo.gameBounds.height.toFloat()
        return PointRelative((x * PRECISION) / PRECISION, (y * PRECISION) / PRECISION)
    }

    fun toPointAbsolute(point: PointRelative): PointAbsolute {
        val x = point.x * GameInfo.gameBounds.width + GameInfo.gameBounds.x
        val y = point.y * GameInfo.gameBounds.height + GameInfo.gameBounds.y
        return PointAbsolute(x.roundToInt(), y.roundToInt())
    }

    fun toRectangleRelative(rect: RectangleAbsolute): RectangleRelative {
        val x1 = (rect.x - GameInfo.gameBounds.x).toFloat() / GameInfo.gameBounds.width.toFloat()
        val y1 = (rect.y - GameInfo.gameBounds.y).toFloat() / GameInfo.gameBounds.height.toFloat()
        val x2 = (rect.x + rect.width - GameInfo.gameBounds.x).toFloat() / GameInfo.gameBounds.width.toFloat()
        val y2 = (rect.y + rect.height - GameInfo.gameBounds.y).toFloat() / GameInfo.gameBounds.height.toFloat()
        return RectangleRelative(
            (x1 * PRECISION) / PRECISION,
            (y1 * PRECISION) / PRECISION,
            ((x2 - x1) * PRECISION) / PRECISION,
            ((y2 - y1) * PRECISION) / PRECISION
        )
    }

    fun toRectangleAbsolute(rect: RectangleRelative): RectangleAbsolute {
        val x1 = rect.x * GameInfo.gameBounds.width + GameInfo.gameBounds.x
        val y1 = rect.y * GameInfo.gameBounds.height + GameInfo.gameBounds.y
        val x2 = (rect.x + rect.width) * GameInfo.gameBounds.width + GameInfo.gameBounds.x
        val y2 = (rect.y + rect.height) * GameInfo.gameBounds.height + GameInfo.gameBounds.y
        return RectangleAbsolute(x1.roundToInt(), y1.roundToInt(), (x2 - x1).roundToInt(), (y2 - y1).roundToInt())
    }

}

fun Point.toPointAbsolute() = PointAbsolute(x - GameInfo.gameBounds.x, y - GameInfo.gameBounds.y)
fun Point.toPointRelative() = ConverterUtil.toPointRelative(this.toPointAbsolute())
fun PointAbsolute.toPointRelative() = ConverterUtil.toPointRelative(this)
fun PointAbsolute.toPoint() = ConverterUtil.toPoint(this)
fun PointRelative.toPointAbsolute() = ConverterUtil.toPointAbsolute(this)
fun RectangleAbsolute.toRectangleRelative() = ConverterUtil.toRectangleRelative(this)
fun RectangleAbsolute.toRectangle() = ConverterUtil.toRectangle(this)
fun RectangleRelative.toRectangleAbsolute() = ConverterUtil.toRectangleAbsolute(this)
