package com.galeegaharditama.cc.common.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun TopBar(
  title: String,
  backgroundColor: Color = MaterialTheme.colors.surface,
  navigationIcon: @Composable (() -> Unit)? = null,
  actions: @Composable RowScope.() -> Unit = {},
) {
  TopAppBar(
    title = {
      Text(
        text = title,
        style = MaterialTheme.typography.h6
      )
    },
    backgroundColor = backgroundColor,
    actions = actions,
    navigationIcon = navigationIcon
  )
}
