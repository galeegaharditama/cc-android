package com.galeegaharditama.cc.register

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.galeegaharditama.cc.common.StatefulViewModel
import com.galeegaharditama.cc.domain.model.LevelUser
import com.galih.library.onFailure
import com.galih.library.onSuccess
import com.galih.library.validation.ErrorValidationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class RegisterViewModel(
  private val interactor: RegisterInteractors
) : StatefulViewModel<RegisterState, RegisterEffect, RegisterAction, RegisterInteractors>(
  RegisterState(), interactor
) {

  init {
    dispatch(RegisterAction.Load)
  }

  override fun dispatch(action: RegisterAction) {
    when (action) {
      is RegisterAction.Load -> {
        onLoadData()
      }
      is RegisterAction.ChangeFirstName -> {
        onFirstNameChanged(action.value)
      }
      is RegisterAction.ChangeLastName -> {
        onLastNameChanged(action.value)
      }
      is RegisterAction.ChangeEmail -> {
        onEmailChanged(action.value)
      }
      is RegisterAction.ChangePassword -> {
        onPasswordChanged(action.value)
      }
      is RegisterAction.ChangeConfirmPassword -> {
        onConfirmPasswordChanged(action.value)
      }
      is RegisterAction.ChangeLevel -> {
        onLevelChanged(action.value)
      }
      is RegisterAction.ClickRegister -> {
        viewModelScope.launch {
          onValidation()

          onSubmit()
        }
      }
    }
  }

  private fun onLoadData() {
    viewModelScope.launch {
      setEffect(RegisterEffect.Loading)
      val onLoadData = async { interactor.getLevelUser() }
      withContext(Dispatchers.Main) {
        val result = onLoadData.await()

        result.onSuccess {
          setState { copy(listLevelUser = it) }
          setEffect(RegisterEffect.Success)
        }.onFailure {
          setEffect(
            RegisterEffect.ShowErrorMessage(
              errorType = it.type, message = it.message ?: ""
            )
          )
        }
      }
    }
  }

  private suspend fun onValidation() {
    if (state.value.firstName.isBlank()) {
      setState { copy(firstNameValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(firstNameValidation = ErrorValidationType.VALID) }

    if (state.value.lastName.isBlank()) {
      setState { copy(lastNameValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(lastNameValidation = ErrorValidationType.VALID) }

    if (state.value.level.slug.isBlank()) {
      setState { copy(levelValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(levelValidation = ErrorValidationType.VALID) }

    if (state.value.email.isBlank()) {
      setState { copy(emailValidation = ErrorValidationType.EMPTY) }
    } else if (!Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()) {
      setState { copy(emailValidation = ErrorValidationType.INVALID_FORMAT) }
    } else setState { copy(emailValidation = ErrorValidationType.VALID) }

    if (state.value.password.isBlank()) {
      setState { copy(passwordValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(passwordValidation = ErrorValidationType.VALID) }

    if (state.value.confirmPassword.isBlank()) {
      setState { copy(confirmPasswordValidation = ErrorValidationType.EMPTY) }
    } else if (state.value.confirmPassword != state.value.password) {
      setState { copy(confirmPasswordValidation = ErrorValidationType.PASSWORD_NOT_MATCHING) }
    } else setState { copy(confirmPasswordValidation = ErrorValidationType.VALID) }
  }

  private fun onSubmit() {
    viewModelScope.launch(Dispatchers.IO) {
      if (!state.value.isAllValid) return@launch

      val onRegister = async {
        interactor.register(
          state.value.firstName,
          state.value.lastName,
          state.value.email,
          state.value.password,
          state.value.level.slug
        )
      }
      withContext(Dispatchers.Main) {
        val result = onRegister.await()
        result.onSuccess {
          setEffect(RegisterEffect.SuccessRegister)
        }.onFailure {
          setEffect(
            RegisterEffect.ShowErrorMessage(
              errorType = it.type, message = it.message ?: ""
            )
          )
        }
      }
    }
  }

  private fun onFirstNameChanged(value: String) {
    viewModelScope.launch {
      setState { copy(firstName = value) }
    }
  }

  private fun onLastNameChanged(value: String) {
    viewModelScope.launch {
      setState { copy(lastName = value) }
    }
  }

  private fun onEmailChanged(value: String) {
    viewModelScope.launch {
      setState { copy(email = value) }
    }
  }

  private fun onLevelChanged(value: LevelUser) {
    viewModelScope.launch {
      setState { copy(level = value) }
    }
  }

  private fun onPasswordChanged(value: String) {
    viewModelScope.launch {
      setState { copy(password = value) }
    }
  }

  private fun onConfirmPasswordChanged(value: String) {
    viewModelScope.launch {
      setState { copy(confirmPassword = value) }
    }
  }
}
