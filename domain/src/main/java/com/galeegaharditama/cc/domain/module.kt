package com.galeegaharditama.cc.domain

import com.galeegaharditama.cc.domain.interactor.GetAccount
import com.galeegaharditama.cc.domain.interactor.GetAllCathConference
import com.galeegaharditama.cc.domain.interactor.GetApproval
import com.galeegaharditama.cc.domain.interactor.GetCathConference
import com.galeegaharditama.cc.domain.interactor.GetDpjpTindakan
import com.galeegaharditama.cc.domain.interactor.GetDpjpUtama
import com.galeegaharditama.cc.domain.interactor.GetLevelUser
import com.galeegaharditama.cc.domain.interactor.Login
import com.galeegaharditama.cc.domain.interactor.Logout
import com.galeegaharditama.cc.domain.interactor.Register
import com.galeegaharditama.cc.domain.interactor.SubmitCathConference
import com.galeegaharditama.cc.domain.interactor.UpdateCathConference
import org.koin.dsl.module

val domainModule = module {
  single { Login(get()) }

  single { Register(get()) }

  single { GetLevelUser(get()) }

  single { GetAllCathConference(get()) }

  single { GetCathConference(get()) }

  single { GetAccount(get()) }

  single { Logout(get()) }

  single { GetDpjpUtama(get()) }

  single { GetDpjpTindakan(get()) }

  single { GetApproval(get()) }

  single { SubmitCathConference(get()) }

  single { UpdateCathConference(get()) }
}
