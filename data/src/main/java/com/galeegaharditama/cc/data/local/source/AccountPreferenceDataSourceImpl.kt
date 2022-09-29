package com.galeegaharditama.cc.data.local.source

import android.content.SharedPreferences
import com.galeegaharditama.cc.data.local.base.IAccountPreferenceDataSource
import com.galeegaharditama.cc.data.local.model.AccountCache
import com.galih.library.Output
import com.squareup.moshi.Moshi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class AccountPreferenceDataSourceImpl(
  private val preferenceAccount: SharedPreferences
) : IAccountPreferenceDataSource, KoinComponent {
  override fun get(): Output<AccountCache> {
    val json = preferenceAccount.getString(IAccountPreferenceDataSource.PREFERENCE_ACCOUNT, null)

    if (json.isNullOrBlank()) {
      return Output.failure(exception = Throwable("User Not Found"))
    }

    val jsonAdapter = get<Moshi>().adapter(AccountCache::class.java)
    val account = jsonAdapter.fromJson(json)
    return if (account != null) Output.success(account)
    else Output.failure(exception = Throwable("User Not Found"))
  }

  override fun add(account: AccountCache) {
    val jsonAdapter = get<Moshi>().adapter(AccountCache::class.java)
    val json = jsonAdapter.toJson(account)

    preferenceAccount.edit().apply {
      putString(IAccountPreferenceDataSource.PREFERENCE_ACCOUNT, json)
    }.apply()
  }

  override fun delete() {
    preferenceAccount.edit().apply { clear() }.apply()
  }
}
