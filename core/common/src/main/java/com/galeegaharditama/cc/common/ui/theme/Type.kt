package com.galeegaharditama.cc.common.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.galeegaharditama.cc.common.R

// Set of Material typography styles to start with
private val fontFamilyKulim = FontFamily(
  listOf(
    Font(
      resId = R.font.kulim_park_regular
    ),
    Font(
      resId = R.font.kulim_park_light,
      weight = FontWeight.Light
    )
  )
)

private val fontFamilyLato = FontFamily(
  listOf(
    Font(
      resId = R.font.lato_regular
    ),
    Font(
      resId = R.font.lato_bold,
      weight = FontWeight.Bold
    )
  )
)

val Typography = Typography(
  defaultFontFamily = fontFamilyLato
)
