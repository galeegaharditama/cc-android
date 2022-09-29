package com.galeegaharditama.cc.domain.repository

import com.galeegaharditama.cc.domain.model.Account
import com.galih.library.Output

interface IAccountRepository {
  suspend fun login(email: String, password: String, token: String?): Output<Account>
  fun get(): Output<Account>
  fun logout(): Output<Any>
  suspend fun register(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    level: String
  ): Output<Any>
}
