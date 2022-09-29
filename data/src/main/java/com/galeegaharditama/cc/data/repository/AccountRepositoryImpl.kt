package com.galeegaharditama.cc.data.repository

import com.galeegaharditama.cc.data.local.base.IAccountPreferenceDataSource
import com.galeegaharditama.cc.data.local.model.AccountCache
import com.galeegaharditama.cc.data.local.model.toDomain
import com.galeegaharditama.cc.data.remote.base.IAccountRemoteDataSource
import com.galeegaharditama.cc.data.remote.model.toCache
import com.galeegaharditama.cc.data.remote.model.toDomain
import com.galeegaharditama.cc.domain.model.Account
import com.galeegaharditama.cc.domain.repository.IAccountRepository
import com.galih.library.Output
import com.galih.library.onFailure
import com.galih.library.onSuccessWithTransform

internal class AccountRepositoryImpl(
  private val cacheAccountDataSource: IAccountPreferenceDataSource,
  private val remoteAccountDataSource: IAccountRemoteDataSource,
) : IAccountRepository {
  override suspend fun login(email: String, password: String, token: String?): Output<Account> {
    val result = remoteAccountDataSource.login(email, password, token)

    return result.onSuccessWithTransform {
      saveToLocal(it.toCache())
      it.toDomain()
    }.onFailure {
      Output.failure<Account>(type = it.type, status = it.status, message = it.message ?: "")
    }
  }

  override fun get(): Output<Account> {
    val result = cacheAccountDataSource.get()

    return result.onSuccessWithTransform {
      it.toDomain()
    }.onFailure {
      Output.failure<Account>(type = it.type, status = it.status, message = it.message ?: "")
    }
  }

  override fun logout(): Output<Any> {
    cacheAccountDataSource.delete()
    return Output.success("")
  }

  override suspend fun register(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    level: String
  ): Output<Any> {
    val result = remoteAccountDataSource.register(firstName, lastName, email, password, level)

    return result
      .onSuccessWithTransform { it }
      .onFailure {
        Output.failure<Any>(type = it.type, status = it.status, message = it.message ?: "")
      }
  }

  private fun saveToLocal(data: AccountCache) = cacheAccountDataSource.add(data)
}
