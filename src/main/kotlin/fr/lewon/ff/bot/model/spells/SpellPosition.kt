package fr.lewon.ff.bot.model.spells

import fr.lewon.ff.bot.util.filemanagers.impl.GlobalConfigManager
import fr.lewon.ff.bot.util.geometry.PointRelative
import fr.lewon.ff.bot.util.io.toPointRelative

enum class SpellPosition(val getPosition: () -> PointRelative) {
    Left1({ GlobalConfigManager.readConfig().leftSpell1Position.toPointRelative() }),
    Top1({ GlobalConfigManager.readConfig().topSpell1Position.toPointRelative() }),
    Right1({ GlobalConfigManager.readConfig().rightSpell1Position.toPointRelative() }),
    Bottom1({ GlobalConfigManager.readConfig().bottomSpell1Position.toPointRelative() }),
    Left2({ GlobalConfigManager.readConfig().leftSpell2Position.toPointRelative() }),
    Top2({ GlobalConfigManager.readConfig().topSpell2Position.toPointRelative() }),
    Right2({ GlobalConfigManager.readConfig().rightSpell2Position.toPointRelative() }),
    Bottom2({ GlobalConfigManager.readConfig().bottomSpell2Position.toPointRelative() }),
}