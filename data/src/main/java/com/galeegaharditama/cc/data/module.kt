package com.galeegaharditama.cc.data

import android.content.Context
import com.galeegaharditama.cc.data.local.base.IAccountPreferenceDataSource
import com.galeegaharditama.cc.data.local.source.AccountPreferenceDataSourceImpl
import com.galeegaharditama.cc.data.remote.base.IAccountRemoteDataSource
import com.galeegaharditama.cc.data.remote.base.ICathConferenceRemoteDataSource
import com.galeegaharditama.cc.data.remote.base.ILevelUserRemoteDataSource
import com.galeegaharditama.cc.data.remote.service.ApiService
import com.galeegaharditama.cc.data.remote.source.AccountRemoteDataSourceImpl
import com.galeegaharditama.cc.data.remote.source.CathConferenceRemoteDataSourceImpl
import com.galeegaharditama.cc.data.remote.source.LevelUserRemoteDataSourceImpl
import com.galeegaharditama.cc.data.repository.AccountRepositoryImpl
import com.galeegaharditama.cc.data.repository.CathConferenceRepositoryImpl
import com.galeegaharditama.cc.data.repository.LevelUserRepositoryImpl
import com.galeegaharditama.cc.domain.repository.IAccountRepository
import com.galeegaharditama.cc.domain.repository.ICathConferenceRepository
import com.galeegaharditama.cc.domain.repository.ILevelUserRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
  single { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }

  single { get<Retrofit>().create(ApiService::class.java) }

  //region DATASOURCE
  single<IAccountRemoteDataSource> { AccountRemoteDataSourceImpl(service = get()) }
  single<IAccountPreferenceDataSource> {
    AccountPreferenceDataSourceImpl(
      preferenceAccount = androidContext().getSharedPreferences(
        IAccountPreferenceDataSource.PREFERENCE_ACCOUNT,
        Context.MODE_PRIVATE
      )
    )
  }

  single<ILevelUserRemoteDataSource> { LevelUserRemoteDataSourceImpl(get()) }

  single<ICathConferenceRemoteDataSource> { CathConferenceRemoteDataSourceImpl(get()) }
  //endregion

  //region REPOSITORY
  single<IAccountRepository> {
    AccountRepositoryImpl(
      cacheAccountDataSource = get(),
      remoteAccountDataSource = get(),
    )
  }

  single<ILevelUserRepository> {
    LevelUserRepositoryImpl(
      remote = get(),
    )
  }

  single<ICathConferenceRepository> {
    CathConferenceRepositoryImpl(
      remote = get(),
    )
  }
  //endregion
}
