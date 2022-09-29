package com.galeegaharditama.cc.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.galeegaharditama.cc.common.AccountWrapper
import com.galeegaharditama.cc.common.navigation.navigateSingleTopTo
import com.galeegaharditama.cc.dashboard.DashboardNavigation
import com.galeegaharditama.cc.dashboard.DashboardScreen
import com.galeegaharditama.cc.form.FormCreateNavigation
import com.galeegaharditama.cc.form.FormUpdateNavigation
import com.galeegaharditama.cc.form.create.FormCreateScreen
import com.galeegaharditama.cc.form.update.FormUpdateScreen
import com.galeegaharditama.cc.login.LoginNavigation
import com.galeegaharditama.cc.login.LoginScreen
import com.galeegaharditama.cc.register.RegisterNavigation
import com.galeegaharditama.cc.register.RegisterScreen

@Composable
fun CathConferenceNavHost(
  activity: Activity,
  navController: NavHostController,
  modifier: Modifier
) {
  val startRoute = if (AccountWrapper.isLoggedIn) DashboardNavigation.route
  else LoginNavigation.route

  NavHost(
    navController = navController,
    startDestination = startRoute,
    modifier = modifier
  ) {
    composable(route = LoginNavigation.route) {
      LoginScreen(
        onSuccessLogin = {
          navController.navigateSingleTopTo(
            DashboardNavigation.route,
            optionsBuilder = {
              popUpTo(LoginNavigation.route) {
                inclusive = true
              }
            }
          )
        },
        onClickForgot = { /*TODO*/ },
        onClickRegister = {
          navController.navigateSingleTopTo(RegisterNavigation.route)
        }
      )
    }

    composable(route = RegisterNavigation.route) {
      RegisterScreen(
        activity = activity,
        onSuccessRegister = { navController.navigateSingleTopTo(LoginNavigation.route) }
      )
    }

    composable(route = DashboardNavigation.route) {
      DashboardScreen(
        activity = activity,
        onSuccessLogout = {
          navController.navigateSingleTopTo(
            LoginNavigation.route,
            optionsBuilder = {
              popUpTo(DashboardNavigation.route) {
                inclusive = true
              }
            }
          )
        },
        onClickViewAll = {},
        onClickItem = { id ->
          navController.navigateSingleTopTo("${FormUpdateNavigation.route}/$id")
        },
        onClickAdd = {
          navController.navigateSingleTopTo(FormCreateNavigation.route)
        }
      )
    }

    composable(route = FormCreateNavigation.route) {
      FormCreateScreen(
        activity = activity,
        onSuccessSubmit = { /*TODO*/ },
        onClickNavigation = {
          navController.popBackStack()
        }
      )
    }

    composable(
      route = FormUpdateNavigation.routeWithArgs,
      arguments = FormUpdateNavigation.arguments
    ) {
      val id =
        it.arguments?.getString(FormUpdateNavigation.idArg)

      FormUpdateScreen(
        activity = activity,
        id = id?.toInt() ?: 0,
        onSuccessSubmit = { /*TODO*/ },
        onClickNavigation = {
          navController.popBackStack()
        }
      )
    }
  }
}
