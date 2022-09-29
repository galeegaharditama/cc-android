package com.galeegaharditama.cc.domain.model

import com.galih.library.IOutputError

// import com.galih.library.remote.TypeError

class GeneralError(
  val errType: IOutputError? = null,
  val statusCode: Int? = null,
  val errMessage: String,
) : Throwable(errMessage)
