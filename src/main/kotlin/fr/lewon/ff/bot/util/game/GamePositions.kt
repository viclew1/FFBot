package fr.lewon.ff.bot.util.game

import fr.lewon.ff.bot.util.geometry.PointRelative
import fr.lewon.ff.bot.util.geometry.RectangleRelative

object GamePositions {

    private val fishingExclamationMarkTopLeft = PointRelative(695f / 1920f, 244f / 1080f)
    private val fishingExclamationMarkBottomRight = PointRelative(1043 / 1920f, 477f / 1080f)
    val fishingExclamationMarkBounds = RectangleRelative.build(
        topLeft = fishingExclamationMarkTopLeft,
        bottomRight = fishingExclamationMarkBottomRight
    )

}