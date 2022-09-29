package com.galeegaharditama.cc.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
  primary = md_theme_light_primary,
  primaryVariant = md_theme_light_tertiary,
  secondary = md_theme_light_secondary,
  secondaryVariant = md_theme_light_inversePrimary,
  background = md_theme_light_background
)

private val DarkColorPalette = darkColors(
  primary = md_theme_dark_primary,
  primaryVariant = md_theme_dark_tertiary,
  secondary = md_theme_dark_secondary,
  secondaryVariant = md_theme_dark_inversePrimary,
  background = md_theme_dark_background
)

@Composable
fun CCTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}
