package fr.lewon.ff.bot.scripts.impl

import fr.lewon.ff.bot.model.spells.SpellPosition
import fr.lewon.ff.bot.scripts.BotScriptBuilder
import fr.lewon.ff.bot.scripts.BotScriptStat
import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues
import fr.lewon.ff.bot.scripts.parameters.impl.ChoiceParameter
import fr.lewon.ff.bot.util.game.GamePositions
import fr.lewon.ff.bot.util.io.MouseUtil
import fr.lewon.ff.bot.util.io.ScreenUtil
import fr.lewon.ff.bot.util.io.WaitUtil
import fr.lewon.ff.bot.util.logs.LogItem
import java.awt.Color

object FishingScript : BotScriptBuilder("Fishing") {

    private val fishingSpellParameter = ChoiceParameter(
        "Fishing spell location",
        "The location of the fishing spell",
        SpellPosition.Bottom,
        getAvailableValues = { SpellPosition.entries.toList() },
        stringToItemValue = { SpellPosition.valueOf(it) },
        itemValueToString = { it.name }
    )

    private val reelSpellParameter = ChoiceParameter(
        "Reel spell location",
        "The location of the reeling spell",
        SpellPosition.Left,
        getAvailableValues = { SpellPosition.entries.toList() },
        stringToItemValue = { SpellPosition.valueOf(it) },
        itemValueToString = { it.name }
    )

    override fun getParameters(): List<BotParameter<*>> {
        return listOf(fishingSpellParameter, reelSpellParameter)
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
        val fishingSpellPos = parameterValues.getParamValue(fishingSpellParameter)
        val reelingSpellPos = parameterValues.getParamValue(reelSpellParameter)
        while (true) {
            if (!WaitUtil.waitUntilCharacterAvailable()) {
                error("Character isn't available")
            }
            Thread.sleep(300)
            MouseUtil.leftClick(fishingSpellPos.getPosition())
            if (!waitUntilFishCaptured()) {
                error("Couldn't find exclamation mark")
            }
            Thread.sleep(300)
            MouseUtil.leftClick(reelingSpellPos.getPosition())
            Thread.sleep(1000)
        }
    }

    private fun waitUntilFishCaptured(): Boolean = WaitUtil.waitUntil(60_000) {
        val minColor = Color(251, 251, 251)
        val maxColor = Color.WHITE
        ScreenUtil.colorCount(GamePositions.fishingExclamationMarkBounds, minColor, maxColor) > 2000
    }

}