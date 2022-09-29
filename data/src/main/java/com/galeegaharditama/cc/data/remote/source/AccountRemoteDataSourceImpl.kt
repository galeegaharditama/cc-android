package com.galeegaharditama.cc.data.remote.source

import com.galeegaharditama.cc.data.remote.base.IAccountRemoteDataSource
import com.galeegaharditama.cc.data.remote.model.LoginResponse
import com.galeegaharditama.cc.data.remote.service.ApiService
import com.galih.library.Output

internal class AccountRemoteDataSourceImpl(
  private val service: ApiService
) : IAccountRemoteDataSource {
  override suspend fun login(
    email: String,
    password: String,
    token: String?
  ): Output<LoginResponse> {
    return networkHandler {
      service.login(email, password, token)
    }
  }

  override suspend fun register(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    level: String
  ): Output<Any> {
    return networkHandler { service.register(firstName, lastName, email, password, level) }
  }
}
