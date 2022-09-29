package com.galeegaharditama.cc.register

import com.galeegaharditama.cc.domain.interactor.GetLevelUser
import com.galeegaharditama.cc.domain.interactor.Register

data class RegisterInteractors(
  val getLevelUser: GetLevelUser,
  val register: Register
)
