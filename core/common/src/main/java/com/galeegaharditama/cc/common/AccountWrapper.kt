package com.galeegaharditama.cc.common

import com.galeegaharditama.cc.domain.interactor.GetAccount
import com.galeegaharditama.cc.domain.interactor.getOnSuccess
import com.galeegaharditama.cc.domain.interactor.isLoggedIn
import com.galeegaharditama.cc.domain.model.Account
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object AccountWrapper : KoinComponent {
  private val getAccount: GetAccount = get()

  val account: Account?
    get() = getAccount.getOnSuccess()

  val isLoggedIn: Boolean
    get() = getAccount.isLoggedIn()
}
