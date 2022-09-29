package com.galeegaharditama.cc.common.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CardExpandable(
  modifier: Modifier = Modifier,
  modifierColumn: Modifier = Modifier,
  cardContent: @Composable ColumnScope.() -> Unit,
) {
  Card(
    elevation = 4.dp,
    modifier = modifier
  ) {
    Column(
      modifier = modifierColumn
        .animateContentSize(
          animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
          )
        )
    ) {
      cardContent()
    }
  }
}
