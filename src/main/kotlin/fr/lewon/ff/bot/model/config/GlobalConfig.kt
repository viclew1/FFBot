package fr.lewon.ff.bot.model.config

import java.awt.Point

data class GlobalConfig(
    var isFramed: Boolean = false,
    var leftSpellPosition: Point = Point(),
    var rightSpellPosition: Point = Point(),
    var bottomSpellPosition: Point = Point(),
    var topSpellPosition: Point = Point(),
    var craftPosition: Point = Point(),
    var sprintSpellPosition: Point = Point()
)