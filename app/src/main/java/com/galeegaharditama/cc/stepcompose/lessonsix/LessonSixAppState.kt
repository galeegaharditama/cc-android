package com.galeegaharditama.cc.stepcompose.lessonsix

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppState(
  navController: NavHostController = rememberNavController(),
): LessonSixAppState {
  return remember(navController) {
    LessonSixAppState(navController)
  }
}

@Stable
class LessonSixAppState(
  val navController: NavHostController
) {
  val topBars = tabRowScreens
  private val topBarRoutes = topBars.map { it.route }

  val currentDestination: NavDestination?
    @Composable get() = navController.currentBackStackEntryAsState().value?.destination

  val shouldShowTopBar: Boolean
    @Composable get() {
      return currentDestination?.route in topBarRoutes
    }

  val currentRoute: String?
    @Composable get() = currentDestination?.route
}
