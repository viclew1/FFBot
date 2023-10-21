package fr.lewon.ff.bot.gui.util

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

object AppInfo {

    const val APP_NAME = "VL FFXIV Bot"

    @Composable
    fun mainTheme(typography: Typography, content: @Composable () -> Unit) {
        MaterialTheme(
            colors = darkColors(
                primary = AppColors.primaryColor,
                primaryVariant = AppColors.primaryDarkColor,
                secondary = AppColors.primaryColor,
                secondaryVariant = AppColors.primaryLightColor,
                onPrimary = AppColors.primaryTextColor,
                onSecondary = AppColors.primaryTextColor,
            ),
            typography = typography,
            shapes = Shapes(),
            content = content
        )
    }

}