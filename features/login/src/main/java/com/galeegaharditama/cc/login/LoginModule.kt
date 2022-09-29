package com.galeegaharditama.cc.login

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
  //region interactore
  factory { LoginInteractors(login = get()) }
  //endregion

  // region ViewModels
  viewModel {
    LoginViewModel(interactor = get())
  }
  //endregion
}
