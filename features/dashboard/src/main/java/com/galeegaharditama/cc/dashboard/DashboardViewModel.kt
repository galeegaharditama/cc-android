package com.galeegaharditama.cc.dashboard

import androidx.lifecycle.viewModelScope
import com.galeegaharditama.cc.common.StatefulViewModel
import com.galeegaharditama.cc.dashboard.model.toDashboardDataViews
import com.galih.library.OutputError
import com.galih.library.onFailure
import com.galih.library.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class DashboardViewModel(
  private val interactor: DashboardInteractors,
) : StatefulViewModel<DashboardState, DashboardEffect, DashboardAction, DashboardInteractors>(
  DashboardState(), interactor
) {
  init {
    dispatch(DashboardAction.Load)
  }

  override fun dispatch(action: DashboardAction) {
    when (action) {
      DashboardAction.Load, DashboardAction.Refresh -> {
        onLoadData()
      }
      DashboardAction.ClickViewAll -> {}
      DashboardAction.ClickLogout -> {
        onLogout()
      }
      DashboardAction.ClickAddCathConference -> {}
      is DashboardAction.ClickItemCathConference -> {
      }
    }
  }

  private fun onLogout() {
    viewModelScope.launch {
      val result = interactor.logout()
      if (result.isSuccess) setEffect(DashboardEffect.SuccessLogout)
    }
  }

  private fun onLoadData() {
    viewModelScope.launch {
      setState { copy(isLoadingContent = true) }

      val loadData = async { interactor.cathConferences() }

      withContext(Dispatchers.Main) {
        val result = loadData.await()
        result.onSuccess {
          setState {
            copy(
              listCathConference = it.toDashboardDataViews(),
              isLoadingContent = false
            )
          }
        }.onFailure {
          setState { copy(isLoadingContent = false) }
          when(it.type) {
            OutputError.NETWORK -> setEffect(DashboardEffect.LostConnection)
            OutputError.SERVER -> {
              if (it.status == 500) setEffect(DashboardEffect.ServerError)
            }
            else -> setEffect(DashboardEffect.ErrorGetData(it.message ?: ""))
          }
        }
      }
    }
  }
}
