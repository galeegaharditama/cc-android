package com.galeegaharditama.cc.data.remote.model

import com.galih.library.remote.BaseError

data class BaseResponse<T : Any>(
  val message: String? = null,
  val data: T? = null
)

data class ErrorResponse(
  val status: Int?,
  val code: Int?,
  val message: String?,
  val name: String?
) : BaseError {
  override val errMessage: String
    get() = message ?: ""
  override val errStatus: Int
    get() = status ?: 0
}
