package com.galeegaharditama.cc.common.ui.component

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingShimmer(
  modifier: Modifier = Modifier
){

  //These colors will be used on the brush. The lightest color should be in the middle

  val gradient = listOf(
    Color.LightGray.copy(alpha = 0.9f), //darker grey (90% opacity)
    Color.LightGray.copy(alpha = 0.3f), //lighter grey (30% opacity)
    Color.LightGray.copy(alpha = 0.9f)
  )

  val transition = rememberInfiniteTransition() // animate infinite times

  val translateAnimation = transition.animateFloat( //animate the transition
    initialValue = 0f,
    targetValue = 1000f,
    animationSpec = infiniteRepeatable(
      animation = tween(
        durationMillis = 1000, // duration for the animation
        easing = FastOutLinearInEasing
      )
    )
  )
  val brush = linearGradient(
    colors = gradient,
    start = Offset(200f, 200f),
    end = Offset(x = translateAnimation.value,
      y = translateAnimation.value)
  )
  Spacer(modifier = modifier
    .background(brush)
  )
}
