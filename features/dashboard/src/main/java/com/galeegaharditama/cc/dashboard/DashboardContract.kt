package com.galeegaharditama.cc.dashboard

import com.galeegaharditama.cc.dashboard.model.DashboardItemDataView

internal sealed class DashboardAction {
  object Load : DashboardAction()
  object Refresh : DashboardAction()
  object ClickLogout : DashboardAction()
  data class ClickItemCathConference(val id: Int) : DashboardAction()
  object ClickViewAll : DashboardAction()
  object ClickAddCathConference : DashboardAction()
}

internal data class DashboardState(
  val name: String = "",
  val level: String = "",
  val listCathConference: List<DashboardItemDataView> = listOf(),
  val isLoadingContent: Boolean = false,
)

internal sealed class DashboardEffect {
  object LostConnection : DashboardEffect()
  object ServerError : DashboardEffect()
  data class ErrorGetData(val message: String) : DashboardEffect()
  object SuccessLogout : DashboardEffect()
}
