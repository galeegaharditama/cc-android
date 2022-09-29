package com.galeegaharditama.cc.stepcompose.lessonsix

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

interface Navigation {
  val route: String
}

interface NavigationHome : Navigation {
  val icon: ImageVector
}

/**
 * Rally app navigation destinations
 */
object Overview : NavigationHome {
  override val icon = Icons.Filled.PieChart
  override val route = "overview"
}

object Accounts : NavigationHome {
  override val icon = Icons.Filled.AttachMoney
  override val route = "accounts"
}

object Bills : NavigationHome {
  override val icon = Icons.Filled.MoneyOff
  override val route = "bills"
}

object SingleAccount : Navigation {
  override val route = "single_account"
  const val accountTypeArg = "account_type"
  val routeWithArgs = "$route/{$accountTypeArg}"
  val arguments = listOf(
    navArgument(accountTypeArg) { type = NavType.StringType }
  )
  val deepLinks = listOf(
    navDeepLink { uriPattern = "rally://$route/{$accountTypeArg}" }
  )
}

// Screens to be displayed in the top RallyTabRow
val tabRowScreens = listOf(Overview, Accounts, Bills)
