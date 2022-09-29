package com.galeegaharditama.cc.data.local.base

import com.galeegaharditama.cc.data.local.model.AccountCache
import com.galih.library.Output

interface IAccountPreferenceDataSource {
  fun get(): Output<AccountCache>
  fun add(account: AccountCache)
  fun delete()

  companion object {
    const val PREFERENCE_ACCOUNT = "pref_account"
  }
}
