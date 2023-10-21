package fr.lewon.ff.bot.gui.util

import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

enum class SoundType(private val soundFileName: String, private val playSoundCondition: () -> Boolean = { true }) {

    FAILED("failed.wav"),
    SUCCEEDED("success.wav");

    private fun buildClip(): Clip {
        val inputStream = javaClass.getResourceAsStream("/sounds/$soundFileName")
            ?: error("Sound IS not found")
        val ais = AudioSystem.getAudioInputStream(BufferedInputStream(inputStream))
        val clip = AudioSystem.getClip()
        clip.open(ais)
        return clip
    }

    fun playSound(forcePlay: Boolean = false) {
        if (forcePlay || playSoundCondition()) {
            Thread { buildClip().start() }.start()
        }
    }

}