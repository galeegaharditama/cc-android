package com.galeegaharditama.cc.form

import com.galeegaharditama.cc.domain.interactor.GetApproval
import com.galeegaharditama.cc.domain.interactor.GetCathConference
import com.galeegaharditama.cc.domain.interactor.GetDpjpTindakan
import com.galeegaharditama.cc.domain.interactor.GetDpjpUtama
import com.galeegaharditama.cc.domain.interactor.SubmitCathConference
import com.galeegaharditama.cc.domain.interactor.UpdateCathConference

data class FormInteractors(
  val getApproval: GetApproval,
  val getDpjpUtama: GetDpjpUtama,
  val getDpjpTindakan: GetDpjpTindakan,
  val getCathConference: GetCathConference,
  val submitCathConference: SubmitCathConference,
  val updateCathConference: UpdateCathConference
)
