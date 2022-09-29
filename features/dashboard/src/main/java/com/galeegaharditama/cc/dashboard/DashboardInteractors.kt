package com.galeegaharditama.cc.dashboard

import com.galeegaharditama.cc.domain.interactor.GetAllCathConference
import com.galeegaharditama.cc.domain.interactor.Logout

data class DashboardInteractors(
  val cathConferences: GetAllCathConference,
  val logout: Logout
)
