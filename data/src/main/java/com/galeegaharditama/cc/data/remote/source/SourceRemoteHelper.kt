package com.galeegaharditama.cc.data.remote.source

import com.galeegaharditama.cc.data.remote.model.BaseResponse
import com.galeegaharditama.cc.domain.model.GeneralError
import com.galih.library.Output
import com.galih.library.OutputError
import com.galih.library.remote.BaseError
import com.galih.library.remote.DataEmptyException
import com.galih.library.remote.NetworkResponse

suspend fun <S : Any, E : BaseError> networkHandler(
  execute: suspend () -> NetworkResponse<BaseResponse<S>, E>
): Output<S> {
  return try {
    when (val result = execute.invoke()) {
      is NetworkResponse.Success -> {
        val baseResponse = result.body.data
        if (baseResponse != null) Output.success(baseResponse)
        else throw DataEmptyException()
      }
      is NetworkResponse.NetworkError -> throw GeneralError(
        OutputError.NETWORK,
        errMessage = result.error.localizedMessage ?: "Network Error"
      )
      is NetworkResponse.UnknownError -> throw GeneralError(
        OutputError.UNKNOWN,
        statusCode = result.code,
        errMessage = result.error.localizedMessage ?: "Unknown Error"
      )
      is NetworkResponse.ServerError -> throw GeneralError(
        OutputError.SERVER,
        statusCode = result.code,
        errMessage = result.body?.errMessage ?: "Server Error"
      )
    }
  } catch (e: GeneralError) {
    Output.failure(e.errType, e.statusCode, message = e.errMessage)
  } catch (e: DataEmptyException) {
    Output.failure(type = OutputError.EMPTY, message = e.message ?: e.localizedMessage)
  } catch (e: Throwable) {
    Output.failure(exception = e)
  }
}
