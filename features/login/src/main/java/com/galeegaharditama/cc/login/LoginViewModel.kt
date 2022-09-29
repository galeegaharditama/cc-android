package com.galeegaharditama.cc.login

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.galeegaharditama.cc.common.StatefulViewModel
import com.galih.library.onFailure
import com.galih.library.onSuccess
import com.galih.library.validation.ErrorValidationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class LoginViewModel(
  private val interactor: LoginInteractors
) : StatefulViewModel<LoginState, LoginEffect, LoginAction, LoginInteractors>(
  LoginState(), interactor
) {
  override fun dispatch(action: LoginAction) {
    when (action) {
      is LoginAction.TokenGenerate -> onTokenGenerated(action.value)
      is LoginAction.ChangeEmail -> onEmailChanged(action.value)
      is LoginAction.ChangePassword -> onPasswordChanged(action.value)
      is LoginAction.ClickLogin -> {
        viewModelScope.launch {
          onValidation()

          onSubmit()
        }
      }
    }
  }

  private suspend fun onValidation() {
    if (state.value.email.isBlank()) {
      setState { copy(emailValidation = ErrorValidationType.EMPTY) }
    } else if (!Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()) {
      setState { copy(emailValidation = ErrorValidationType.INVALID_FORMAT) }
    } else setState { copy(emailValidation = ErrorValidationType.VALID) }

    if (state.value.password.isBlank()) {
      setState { copy(passwordValidation = ErrorValidationType.EMPTY) }
    } else setState { copy(passwordValidation = ErrorValidationType.VALID) }
  }

  private fun onSubmit() {
    viewModelScope.launch(Dispatchers.IO) {
      if (!state.value.isAllValid) return@launch
      setState { copy(isLoading = true) }

      val onLogin =
        async { interactor.login(state.value.email, state.value.password, state.value.token) }
      withContext(Dispatchers.Main) {
        val result = onLogin.await()
        result.onSuccess {
          setState { copy(isLoading = false) }
          setEffect(LoginEffect.SuccessLogin)
        }.onFailure {
          setState { copy(isLoading = false) }
          setEffect(
            LoginEffect.ShowErrorMessage(
              errorType = it.type, message = it.message ?: ""
            )
          )
        }
      }
    }
  }

  private fun onEmailChanged(value: String) {
    viewModelScope.launch {
      setState { copy(email = value) }
      if (state.value.email.isNotBlank()) {
        setState { copy(emailValidation = null) }
      }
    }
  }

  private fun onPasswordChanged(value: String) {
    viewModelScope.launch {
      setState { copy(password = value) }
      onValidation()
    }
  }

  private fun onTokenGenerated(value: String) {
    viewModelScope.launch {
      setState { copy(token = value) }
    }
  }
}
