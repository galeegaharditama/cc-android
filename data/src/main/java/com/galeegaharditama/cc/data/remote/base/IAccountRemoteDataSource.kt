package com.galeegaharditama.cc.data.remote.base

import com.galeegaharditama.cc.data.remote.model.LoginResponse
import com.galih.library.Output

interface IAccountRemoteDataSource {
  suspend fun login(email: String, password: String, token: String?): Output<LoginResponse>
  suspend fun register(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    level: String
  ): Output<Any>
}
