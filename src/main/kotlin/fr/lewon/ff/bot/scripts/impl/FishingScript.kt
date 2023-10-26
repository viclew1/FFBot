package fr.lewon.ff.bot.scripts.impl

import fr.lewon.ff.bot.model.spells.SpellPosition
import fr.lewon.ff.bot.scripts.BotScriptBuilder
import fr.lewon.ff.bot.scripts.BotScriptStat
import fr.lewon.ff.bot.scripts.parameters.BotParameter
import fr.lewon.ff.bot.scripts.parameters.ParameterValues
import fr.lewon.ff.bot.scripts.parameters.impl.BooleanParameter
import fr.lewon.ff.bot.scripts.parameters.impl.ChoiceParameter
import fr.lewon.ff.bot.util.game.GamePositions
import fr.lewon.ff.bot.util.io.MouseUtil
import fr.lewon.ff.bot.util.io.ScreenUtil
import fr.lewon.ff.bot.util.io.WaitUtil
import fr.lewon.ff.bot.util.logs.LogItem
import java.awt.Color

object FishingScript : BotScriptBuilder("Fishing") {

    private val minExclamationMarkColor = Color(245, 245, 245)
    private val maxExclamationMarkColor = Color.WHITE

    private val fishingSpellParameter = ChoiceParameter(
        "Fishing spell location",
        "The location of the fishing spell",
        SpellPosition.Bottom1,
        getAvailableValues = { SpellPosition.entries.toList() },
        stringToItemValue = { SpellPosition.valueOf(it) },
        itemValueToString = { it.name }
    )

    private val reelSpellParameter = ChoiceParameter(
        "Reel spell location",
        "The location of the reeling spell",
        SpellPosition.Left1,
        getAvailableValues = { SpellPosition.entries.toList() },
        stringToItemValue = { SpellPosition.valueOf(it) },
        itemValueToString = { it.name }
    )

    private val pecheAuVifParameter = BooleanParameter(
        "Peche au vif",
        "Tu veux pecher au vif? Coche.",
        defaultValue = false
    )

    private val pecheAuVifSpellParameter = ChoiceParameter(
        "Peche au vif spell location",
        "The location of the peche au vif spell",
        SpellPosition.Left2,
        getAvailableValues = { SpellPosition.entries.toList() },
        stringToItemValue = { SpellPosition.valueOf(it) },
        itemValueToString = { it.name },
        displayCondition = { it.getParamValue(pecheAuVifParameter) }
    )

    override fun getParameters(): List<BotParameter<*>> {
        return listOf(fishingSpellParameter, reelSpellParameter, pecheAuVifParameter, pecheAuVifSpellParameter)
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
        val shouldPecheAuVif = parameterValues.getParamValue(pecheAuVifParameter)
        val pecheAuVifSpellPos = parameterValues.getParamValue(pecheAuVifSpellParameter)
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
            if (shouldPecheAuVif) {
                Thread.sleep(1000)
                if (!WaitUtil.waitUntilCharacterAvailable()) {
                    error("Character isn't available")
                }
                MouseUtil.leftClick(pecheAuVifSpellPos.getPosition())
            }
            Thread.sleep(1000)
        }
    }

    private fun waitUntilFishCaptured(): Boolean = WaitUtil.waitUntil(60_000) {
        ScreenUtil.colorCount(
            GamePositions.fishingExclamationMarkBounds,
            minExclamationMarkColor,
            maxExclamationMarkColor
        ) > 3000
    }

}