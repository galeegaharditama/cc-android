package com.galeegaharditama.cc.common.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

fun NavHostController.navigateSingleTopTo(
  route: String,
  optionsBuilder: (NavOptionsBuilder.() -> Unit)? = null,
) =
  this.navigate(route) {
    // Avoid multiple copies of the same destination when
    // reselecting the same item
    launchSingleTop = true
    optionsBuilder?.invoke(this)
  }
