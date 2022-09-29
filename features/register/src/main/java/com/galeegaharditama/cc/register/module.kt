package com.galeegaharditama.cc.register

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerModule = module {
  //region interactore
  factory { RegisterInteractors(register = get(), getLevelUser = get()) }
  //endregion

  // region ViewModels
  viewModel {
    RegisterViewModel(interactor = get())
  }
  //endregion
}
