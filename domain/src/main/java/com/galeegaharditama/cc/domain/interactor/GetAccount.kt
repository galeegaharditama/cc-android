package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.model.Account
import com.galeegaharditama.cc.domain.repository.IAccountRepository
import com.galih.library.onSuccess

class GetAccount(
  private val repository: IAccountRepository
) {
  operator fun invoke() = repository.get()
}

/**
 * Return true when the result is [Result.Success].
 * Return false otherwise.
 */
fun GetAccount.isLoggedIn(): Boolean {
  return this().isSuccess
}

/**
 * Return [Account] when the result is [Result.Success].
 * Return null otherwise.
 */
fun GetAccount.getOnSuccess(): Account? {
  this()
    .onSuccess { return it }
  return null
}
