package com.galeegaharditama.cc.register

import com.galeegaharditama.cc.domain.model.LevelUser
import com.galih.library.IOutputError
import com.galih.library.validation.ErrorValidationType

internal sealed class RegisterAction {
  object Load : RegisterAction()
  data class ChangeFirstName(val value: String) : RegisterAction()
  data class ChangeLastName(val value: String) : RegisterAction()
  data class ChangeEmail(val value: String) : RegisterAction()
  data class ChangePassword(val value: String) : RegisterAction()
  data class ChangeConfirmPassword(val value: String) : RegisterAction()
  data class ChangeLevel(val value: LevelUser) : RegisterAction()
  object ClickRegister : RegisterAction()
}

internal data class RegisterState(
  val firstName: String = "",
  val firstNameValidation: ErrorValidationType? = null,
  val lastName: String = "",
  val lastNameValidation: ErrorValidationType? = null,
  val email: String = "",
  val emailValidation: ErrorValidationType? = null,
  val password: String = "",
  val passwordValidation: ErrorValidationType? = null,
  val confirmPassword: String = "",
  val confirmPasswordValidation: ErrorValidationType? = null,
  val level: LevelUser = LevelUser("", ""),
  val levelValidation: ErrorValidationType? = null,
  val token: String = "",
  val listLevelUser: List<LevelUser> = listOf()
) {
  val isAllValid: Boolean
    get() = emailValidation == ErrorValidationType.VALID &&
      passwordValidation == ErrorValidationType.VALID &&
      confirmPasswordValidation == ErrorValidationType.VALID &&
      levelValidation == ErrorValidationType.VALID &&
      firstNameValidation == ErrorValidationType.VALID &&
      lastNameValidation == ErrorValidationType.VALID
}

internal sealed class RegisterEffect {
  object Loading : RegisterEffect()
  object Success : RegisterEffect()
  object SuccessRegister : RegisterEffect()
  data class ShowErrorMessage(
    val errorType: IOutputError?,
    val message: String
  ) : RegisterEffect()
}
