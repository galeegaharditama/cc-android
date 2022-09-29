package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.repository.IAccountRepository

class Logout(
  private val repository: IAccountRepository
) {
  operator fun invoke() = repository.logout()
}
