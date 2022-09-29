package com.galeegaharditama.cc.common.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextError(modifier: Modifier = Modifier, value: String) {
  Text(
    text = value,
    modifier = modifier,
    color = MaterialTheme.colors.error,
    style = MaterialTheme.typography.caption
  )
}
