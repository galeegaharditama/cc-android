package com.galeegaharditama.cc.form

import com.galeegaharditama.cc.form.create.FormCreateViewModel
import com.galeegaharditama.cc.form.update.FormUpdateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val formModule = module {
  //region interactore
  factory {
    FormInteractors(
      getApproval = get(),
      getDpjpUtama = get(),
      getDpjpTindakan = get(),
      getCathConference = get(),
      submitCathConference = get(),
      updateCathConference = get()
    )
  }
  //endregion

  // region ViewModels
  viewModel {
    FormCreateViewModel(interactor = get())
  }

  viewModel { (id: Int) ->
    FormUpdateViewModel(interactor = get(), id)
  }
  //endregion
}
