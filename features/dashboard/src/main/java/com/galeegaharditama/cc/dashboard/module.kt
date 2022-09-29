package com.galeegaharditama.cc.dashboard

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardModule = module {
  //region interactore
  factory {
    DashboardInteractors(
      cathConferences = get(),
      logout = get()
    )
  }
  //endregion

  // region ViewModels
  viewModel {
    DashboardViewModel(interactor = get())
  }
  //endregion
}
