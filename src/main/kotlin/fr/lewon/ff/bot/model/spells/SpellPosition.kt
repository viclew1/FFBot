package fr.lewon.ff.bot.model.spells

import fr.lewon.ff.bot.util.filemanagers.impl.GlobalConfigManager
import fr.lewon.ff.bot.util.geometry.PointRelative
import fr.lewon.ff.bot.util.io.toPointRelative

enum class SpellPosition(val getPosition: () -> PointRelative) {
    Left({ GlobalConfigManager.readConfig().leftSpellPosition.toPointRelative() }),
    Top({ GlobalConfigManager.readConfig().topSpellPosition.toPointRelative() }),
    Right({ GlobalConfigManager.readConfig().rightSpellPosition.toPointRelative() }),
    Bottom({ GlobalConfigManager.readConfig().bottomSpellPosition.toPointRelative() }),
}