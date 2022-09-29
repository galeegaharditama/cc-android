package com.galeegaharditama.cc.form

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.galeegaharditama.cc.common.navigation.INavigation

object FormCreateNavigation : INavigation {
  override val route: String
    get() = "form-create"
}

object FormUpdateNavigation : INavigation {
  override val route: String
    get() = "form-update"
  const val idArg = "id"
  val routeWithArgs = "$route/{$idArg}"
  val arguments = listOf(
    navArgument(idArg) { type = NavType.StringType }
  )
  val deepLinks = listOf(
    navDeepLink { uriPattern = "rally://$route/{$idArg}" }
  )
}

object FormViewNavigation : INavigation {
  override val route: String
    get() = "form-view"
  const val idArg = "id"
  val routeWithArgs = "$route/{$idArg}"
  val arguments = listOf(
    navArgument(idArg) { type = NavType.StringType }
  )
  val deepLinks = listOf(
    navDeepLink { uriPattern = "rally://$route/{$idArg}" }
  )
}
