package fr.lewon.ff.bot.scripts.impl

import fr.lewon.ff.bot.model.spells.SpellPosition
import fr.lewon.ff.bot.scripts.BotScriptBuilder
import fr.lewon.ff.bot.scripts.BotScriptStat
import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues
import fr.lewon.ff.bot.scripts.parameters.impl.IntParameter
import fr.lewon.ff.bot.scripts.parameters.impl.StringParameter
import fr.lewon.ff.bot.util.filemanagers.impl.GlobalConfigManager
import fr.lewon.ff.bot.util.game.GamePositions
import fr.lewon.ff.bot.util.geometry.RectangleAbsolute
import fr.lewon.ff.bot.util.io.*
import fr.lewon.ff.bot.util.logs.LogItem
import java.awt.Color

object CraftingScript : BotScriptBuilder("Crafting") {

    private val craftKeysParameter = StringParameter(
        "Craft keys",
        "The spells to hit to finish the item (L1,T1,R1,B1,L2,T2,R2,B2), comma separated",
        ""
    )

    private val craftAmountParameter = IntParameter(
        "Craft amount",
        "The amount of items you want to craft",
        1
    )

    override fun getParameters(): List<BotParameter<*>> {
        return listOf(craftKeysParameter, craftAmountParameter)
    }

    override fun getDefaultStats(): List<BotScriptStat> = emptyList()

    override fun getDescription(): String {
        return "Place your camera the closest you can to the character, above its head, and place it in front of a water point."
    }

    override fun doExecuteScript(
        logItem: LogItem,
        parameterValues: ParameterValues,
        statValues: HashMap<BotScriptStat, String>
    ) {
        val craftAmount = parameterValues.getParamValue(craftAmountParameter)
        val craftKeys = parameterValues.getParamValue(craftKeysParameter).split(",").map(this::getSpellPosition)
        if (craftKeys.isEmpty()) {
            error("You need at least one craft key")
        }
        for (i in 0..<craftAmount) {
            if (!waitUntilCraftButtonAvailable()) {
                error("Couldn't find craft button")
            }
            MouseUtil.leftClick(GlobalConfigManager.readConfig().craftPosition.toPointRelative())
            for (craftKey in craftKeys) {
                WaitUtil.sleep(300)
                if (!WaitUtil.waitUntilCharacterAvailable()) {
                    error("Character unavailable")
                }
                WaitUtil.sleep(300)
                MouseUtil.leftClick(craftKey.getPosition())
            }
        }
    }

    private fun waitUntilCraftButtonAvailable(): Boolean {
        val craftPos = GlobalConfigManager.readConfig().craftPosition.toPointAbsolute()
        val bounds = RectangleAbsolute(
            craftPos.x - 25,
            craftPos.y - 15,
            50,
            30
        )
        val minColor1 = Color(117, 99, 59)
        val maxColor1 = Color(121, 101, 63)
        val minColor2 = Color(174, 136, 76)
        val maxColor2 = Color(178, 140, 80)
        return WaitUtil.waitUntil(60_000) {
            ScreenUtil.colorCount(bounds, minColor1, maxColor1) > 5 &&
                ScreenUtil.colorCount(bounds, minColor2, maxColor2) > 5
        }
    }

    private fun getSpellPosition(craftKey: String): SpellPosition {
        return when (craftKey) {
            "L1" -> SpellPosition.Left1
            "R1" -> SpellPosition.Right1
            "T1" -> SpellPosition.Top1
            "B1" -> SpellPosition.Bottom1
            "L2" -> SpellPosition.Left2
            "R2" -> SpellPosition.Right2
            "T2" -> SpellPosition.Top2
            "B2" -> SpellPosition.Bottom2
            else -> error("Invalid key : $craftKey")
        }
    }

    private fun waitUntilFishCaptured(): Boolean = WaitUtil.waitUntil(60_000) {
        val minColor = Color(251, 251, 251)
        val maxColor = Color.WHITE
        ScreenUtil.colorCount(GamePositions.fishingExclamationMarkBounds, minColor, maxColor) > 2000
    }

}