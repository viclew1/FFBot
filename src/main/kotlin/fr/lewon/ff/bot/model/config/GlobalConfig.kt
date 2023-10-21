package fr.lewon.ff.bot.model.config

import java.awt.Point

data class GlobalConfig(
    var isFramed: Boolean = false,
    var leftSpell1Position: Point = Point(),
    var rightSpell1Position: Point = Point(),
    var bottomSpell1Position: Point = Point(),
    var topSpell1Position: Point = Point(),
    var leftSpell2Position: Point = Point(),
    var rightSpell2Position: Point = Point(),
    var bottomSpell2Position: Point = Point(),
    var topSpell2Position: Point = Point(),
    var craftPosition: Point = Point(),
    var sprintSpellPosition: Point = Point()
)