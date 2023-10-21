package fr.lewon.ff.bot.scripts

import fr.lewon.ff.bot.scripts.impl.CraftingScript
import fr.lewon.ff.bot.scripts.impl.FishingScript
import fr.lewon.ff.bot.scripts.impl.dev.TestScriptBuilder

enum class BotScriptBuilders(val builder: BotScriptBuilder) {

    FISHING_SCRIPT(FishingScript),
    CRAFTING_SCRIPT(CraftingScript),
    TEST(TestScriptBuilder);

}