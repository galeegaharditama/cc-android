package com.galeegaharditama.cc.stepcompose.lessonsix

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.galeegaharditama.cc.stepcompose.lessonsix.screen.AccountsScreen
import com.galeegaharditama.cc.stepcompose.lessonsix.screen.BillsScreen
import com.galeegaharditama.cc.stepcompose.lessonsix.screen.OverviewScreen
import com.galeegaharditama.cc.stepcompose.lessonsix.screen.SingleAccountScreen

@Composable
fun LessonNavHost(
  navController: NavHostController,
  modifier: Modifier
) {
  NavHost(navController = navController, startDestination = Overview.route, modifier = modifier) {
    composable(route = Overview.route) {
      OverviewScreen(
//                onClickSeeAllAccounts = {
//                    navController.navigateSingleTopTo(Accounts.route)
//                },
//                onClickSeeAllBills = {
//                    navController.navigateSingleTopTo(Bills.route)
//                },
//                onAccountClick = { accountType ->
//                    navController.navigateToSingleAccount(accountType)
//                }
        onAccountClick = {
          navController.navigateToSingleAccount()
        }
      )
    }
    composable(route = Accounts.route) {
      AccountsScreen(
//                onAccountClick = { accountType ->
//                    navController.navigateToSingleAccount(accountType)
//                }
      )
    }
    composable(route = Bills.route) {
      BillsScreen()
    }
    composable(
      route = SingleAccount.route
    ) {
      SingleAccountScreen()
    }
//        composable(
//            route = SingleAccount.routeWithArgs,
//            arguments = SingleAccount.arguments,
//            deepLinks = SingleAccount.deepLinks
//        ) { navBackStackEntry ->
//            val accountType =
//                navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
//            SingleAccountScreen(accountType)
//        }
  }
}

fun NavHostController.navigateSingleTopToTest(route: String) =
  this.navigate(route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(
      this@navigateSingleTopToTest.graph.findStartDestination().id
    ) {
      saveState()
    }
    // Avoid multiple copies of the same destination when
    // reselecting the same item
    launchSingleTop = true
  }

private fun NavHostController.navigateToSingleAccount(accountType: String) {
  this.navigateSingleTopToTest("${SingleAccount.route}/$accountType")
}

private fun NavHostController.navigateToSingleAccount() {
  this.navigateSingleTopToTest(SingleAccount.route)
}
