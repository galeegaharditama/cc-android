package com.galeegaharditama.cc.stepcompose.lessonsix

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import java.util.*

class LessonSixActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CCTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
        ) {
          HomeScreen()
        }
      }
    }
  }
}

@Composable
private fun HomeScreen() {
  val appState = rememberAppState()
  val currentBackStack by appState.navController.currentBackStackEntryAsState()
  val currentDestination = currentBackStack?.destination
  val currentScreen = tabRowScreens.find { screen ->
    currentDestination?.hierarchy?.any { it.route == screen.route } ?: false
  } ?: Overview

  Scaffold(
    topBar = {
      if (appState.shouldShowTopBar)
        TopBarRow(allScreens = appState.topBars, onTabSelected = { newScreen ->
          appState.navController.navigateSingleTopToTest(newScreen.route)
        }, currentScreen = currentScreen)
    }
  ) { innerPadding ->
    LessonNavHost(
      navController = appState.navController,
      modifier = Modifier.padding(innerPadding)
    )
  }
}

@Composable
private fun TopBarRow(
  allScreens: List<NavigationHome>,
  onTabSelected: (NavigationHome) -> Unit,
  currentScreen: Navigation
) {
  Surface(
    Modifier
      .height(TabHeight)
      .fillMaxWidth()
  ) {
    Row(
      Modifier
        .selectableGroup()
        .fillMaxWidth()
    ) {
      allScreens.forEach { screen ->
        TopTabBar(
          text = screen.route,
          icon = screen.icon,
          onSelected = { onTabSelected(screen) },
          selected = currentScreen == screen
        )
      }
    }
  }
}

@Composable
private fun TopTabBar(
  text: String,
  icon: ImageVector,
  onSelected: () -> Unit,
  selected: Boolean
) {
  val color = MaterialTheme.colors.onSurface
  val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
  val animSpec = remember {
    tween<Color>(
      durationMillis = durationMillis,
      easing = LinearEasing,
      delayMillis = TabFadeInAnimationDelay
    )
  }
  val tabTintColor by animateColorAsState(
    targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
    animationSpec = animSpec
  )
  Row(
    modifier = Modifier
      .padding(16.dp)
      .animateContentSize()
      .height(TabHeight)
      .selectable(
        selected = selected,
        onClick = onSelected,
        role = Role.Tab,
        interactionSource = remember { MutableInteractionSource() },
        indication = rememberRipple(
          bounded = false,
          radius = Dp.Unspecified,
          color = Color.Unspecified
        )
      )
      .clearAndSetSemantics { contentDescription = text },
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
    if (selected) {
      Spacer(Modifier.width(12.dp))
      Text(text.uppercase(Locale.getDefault()), color = tabTintColor)
    }
  }
}

@Preview(name = "Preview", showSystemUi = true, showBackground = true)
@Preview(
  name = "Preview Dark",
  showSystemUi = true,
  showBackground = true,
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun Preview() {
  CCTheme {
    HomeScreen()
  }
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.40f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100
