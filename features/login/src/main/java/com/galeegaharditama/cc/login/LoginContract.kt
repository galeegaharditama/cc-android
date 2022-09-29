package com.galeegaharditama.cc.login

import com.galih.library.IOutputError
import com.galih.library.validation.ErrorValidationType

internal sealed class LoginAction {
  data class TokenGenerate(val value: String) : LoginAction()
  data class ChangeEmail(val value: String) : LoginAction()
  data class ChangePassword(val value: String) : LoginAction()
  object ClickLogin : LoginAction()
}

internal data class LoginState(
  val email: String = "",
  val emailValidation: ErrorValidationType? = null,
  val password: String = "",
  val passwordValidation: ErrorValidationType? = null,
  val token: String = "",
  val isLoading: Boolean = false
) {
  val isAllValid: Boolean
    get() = emailValidation == ErrorValidationType.VALID && passwordValidation == ErrorValidationType.VALID
}

internal sealed class LoginEffect {
  object SuccessLogin : LoginEffect()
  data class ShowErrorMessage(
    val errorType: IOutputError?,
    val message: String
  ) : LoginEffect()
}
