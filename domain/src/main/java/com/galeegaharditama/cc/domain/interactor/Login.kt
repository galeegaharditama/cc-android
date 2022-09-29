package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.repository.IAccountRepository

class Login(
  private val accountRepository: IAccountRepository
) {
  suspend operator fun invoke(
    email: String,
    password: String,
    token: String?
  ) = accountRepository.login(email, password, token)
}
