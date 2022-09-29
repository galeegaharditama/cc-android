package com.galeegaharditama.cc.domain.interactor

import com.galeegaharditama.cc.domain.repository.IAccountRepository

class Register(
  private val accountRepository: IAccountRepository
) {
  suspend operator fun invoke(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    level: String
  ) = accountRepository.register(firstName, lastName, email, password, level)
}
