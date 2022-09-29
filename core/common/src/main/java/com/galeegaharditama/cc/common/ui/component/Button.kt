package com.galeegaharditama.cc.common.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun CathConferenceButton(
  modifier: Modifier = Modifier,
  shape: Shape = MaterialTheme.shapes.small,
  isShowLoading: Boolean = false,
  isLoading: Boolean = false,
  enabled: Boolean = true,
  onClick: () -> Unit,
  content: @Composable () -> Unit,
) {
  val isEnabled = if (enabled) !(isShowLoading && isLoading) else enabled
  Button(
    shape = shape,
    onClick = onClick,
    modifier = modifier
      .height(48.dp),
    enabled = isEnabled
  ) {
    if (isShowLoading && isLoading) {
      CircularProgressIndicator(
        color = MaterialTheme.colors.secondaryVariant,
        modifier = Modifier
          .size(32.dp),
        strokeWidth = 3.dp
      )
    } else content()
  }
}

@Composable
fun CathConferenceOutlinedButton(
  modifier: Modifier = Modifier,
  shape: Shape = MaterialTheme.shapes.small,
  isShowLoading: Boolean = false,
  isLoading: Boolean = false,
  enabled: Boolean = true,
  onClick: () -> Unit,
  content: @Composable () -> Unit,
) {
  val isEnabled = if (enabled) !(isShowLoading && isLoading) else enabled
  OutlinedButton(
    shape = shape,
    onClick = onClick,
    modifier = modifier
      .height(48.dp),
    enabled = isEnabled
  ) {
    if (isShowLoading && isLoading) {
      CircularProgressIndicator(
        color = MaterialTheme.colors.secondaryVariant,
        modifier = Modifier
          .size(32.dp),
        strokeWidth = 3.dp
      )
    } else content()
  }
}
