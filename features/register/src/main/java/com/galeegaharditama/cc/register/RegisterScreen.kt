package com.galeegaharditama.cc.register

import android.app.Activity
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galeegaharditama.cc.common.handleEffect
import com.galeegaharditama.cc.common.ui.component.CathConferenceButton
import com.galeegaharditama.cc.common.ui.component.CathConferenceTextField
import com.galeegaharditama.cc.common.ui.component.LoadingContent
import com.galeegaharditama.cc.common.ui.component.bottomsheet.showAsBottomSheet
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import com.galeegaharditama.cc.common.ui.theme.md_theme_dark_outline
import com.galeegaharditama.cc.common.ui.theme.md_theme_light_outline
import com.galeegaharditama.cc.domain.model.LevelUser
import com.galih.library.validation.ErrorValidationType
import org.koin.androidx.compose.getViewModel
import com.galeegaharditama.cc.common.R as commonR

@Composable
fun RegisterScreen(
  activity: Activity,
  onSuccessRegister: () -> Unit,
) {
  val viewModel = getViewModel<RegisterViewModel>()
  val state = viewModel.state.collectAsState()

  val context = LocalContext.current
  val isLoading = rememberSaveable { mutableStateOf(false) }

  viewModel.handleEffect {
    when (it) {
      is RegisterEffect.Loading -> {
        isLoading.value = true
      }
      is RegisterEffect.Success -> {
        isLoading.value = false
      }
      is RegisterEffect.SuccessRegister -> {
        val msgSuccess = context.getString(R.string.register_msg_success)
        Toast.makeText(context, msgSuccess, Toast.LENGTH_SHORT).show()
        onSuccessRegister.invoke()
      }
      is RegisterEffect.ShowErrorMessage -> {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
      }
    }
  }

  RegisterContent(
    activity = activity,
    state = state.value,
    onFirstNameChanged = { viewModel.dispatch(RegisterAction.ChangeFirstName(it)) },
    onLastNameChanged = { viewModel.dispatch(RegisterAction.ChangeLastName(it)) },
    onEmailChanged = { viewModel.dispatch(RegisterAction.ChangeEmail(it)) },
    onPasswordChanged = { viewModel.dispatch(RegisterAction.ChangePassword(it)) },
    onConfirmPasswordChanged = {
      viewModel.dispatch(
        RegisterAction.ChangeConfirmPassword(
          it
        )
      )
    },
    onLevelChanged = { viewModel.dispatch(RegisterAction.ChangeLevel(it)) },
    onClickRegister = { viewModel.dispatch(RegisterAction.ClickRegister) },
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun RegisterContent(
  activity: Activity? = null,
  isLoading: Boolean = false,
  state: RegisterState = RegisterState(),
  onFirstNameChanged: (String) -> Unit = {},
  onLastNameChanged: (String) -> Unit = {},
  onEmailChanged: (String) -> Unit = {},
  onPasswordChanged: (String) -> Unit = {},
  onConfirmPasswordChanged: (String) -> Unit = {},
  onLevelChanged: (LevelUser) -> Unit = {},
  onClickRegister: () -> Unit = {}
) {
  val listState = rememberLazyListState()
  Box {
    Surface(modifier = Modifier.fillMaxSize()) {}
    Surface(
      modifier = Modifier
        .align(Alignment.TopCenter)
        .size(500.dp)
        .offset(y = (-200).dp),
      color = MaterialTheme.colors.primary,
      shape = RoundedCornerShape(50.dp)
    ) {}
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      state = listState
    ) {
      item {
        HeaderLogo(
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Center)
        )
      }
      item {
        Title(modifier = Modifier.fillMaxWidth())
      }
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          elevation = 8.dp
        ) {
          Column {
            if (isLoading) {
              LoadingContent()
            } else {
              Form(
                activity = activity,
                state = state,
                onFirstNameChanged = onFirstNameChanged,
                onLastNameChanged = onLastNameChanged,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                onConfirmPasswordChanged = onConfirmPasswordChanged,
                onLevelChanged = onLevelChanged,
                onClickRegister = onClickRegister,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun HeaderLogo(modifier: Modifier = Modifier) {
  Image(
    modifier = modifier
      .padding(16.dp)
      .size(100.dp),
    painter = painterResource(id = commonR.drawable.logo_rs),
    contentDescription = null,
  )
}

@Composable
private fun Title(modifier: Modifier = Modifier) {
  Text(
    text = stringResource(id = R.string.register_app_name),
    style = MaterialTheme.typography.h3.copy(
      fontWeight = FontWeight.Light, fontSize = 30.sp,
      color = MaterialTheme.colors.onPrimary
    ),
    modifier = modifier
      .padding(8.dp)
      .fillMaxWidth(),
    textAlign = TextAlign.Center
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Form(
  activity: Activity? = null,
  state: RegisterState,
  onFirstNameChanged: (String) -> Unit,
  onLastNameChanged: (String) -> Unit,
  onEmailChanged: (String) -> Unit,
  onPasswordChanged: (String) -> Unit,
  onConfirmPasswordChanged: (String) -> Unit,
  onLevelChanged: (LevelUser) -> Unit,
  onClickRegister: () -> Unit,
) {
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

  Column {
    Spacer(modifier = Modifier.height(16.dp))
    Text(
      text = stringResource(id = R.string.register_title),
      style = MaterialTheme.typography.h4.copy(
        fontSize = 24.sp,
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
      ),
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
      textAlign = TextAlign.Center
    )
    Text(
      text = stringResource(id = R.string.register_subtitle),
      style = MaterialTheme.typography.h4.copy(
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,
        color = if (isSystemInDarkTheme()) {
          md_theme_light_outline
        } else md_theme_dark_outline
      ),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(24.dp))
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 2.dp)
        .fillMaxWidth(),
      label = commonR.string.first_name,
      value = state.firstName,
      isError = state.firstNameValidation != null && state.firstNameValidation != ErrorValidationType.VALID,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
      onChanged = onFirstNameChanged,
      textError = when (state.firstNameValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = commonR.string.input_blank)
        else -> null
      }
    )
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 2.dp)
        .fillMaxWidth(),
      label = commonR.string.last_name,
      value = state.lastName,
      isError = state.lastNameValidation != null && state.lastNameValidation != ErrorValidationType.VALID,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
      onChanged = onLastNameChanged,
      textError = when (state.lastNameValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = commonR.string.input_blank)
        else -> null
      }
    )
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 2.dp)
        .fillMaxWidth(),
      label = commonR.string.email,
      value = state.email,
      isError = state.emailValidation != null && state.emailValidation != ErrorValidationType.VALID,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
      onChanged = onEmailChanged,
      textError = when (state.emailValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = commonR.string.input_blank)
        ErrorValidationType.INVALID_FORMAT -> stringResource(id = commonR.string.email_invalid)
        else -> null
      }
    )
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 2.dp)
        .fillMaxWidth(),
      label = commonR.string.password,
      value = state.password,
      isError = state.passwordValidation != null && state.passwordValidation != ErrorValidationType.VALID,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
      onChanged = onPasswordChanged,
      trailingIcon = {
        val image = if (passwordVisible) Icons.Filled.Visibility
        else Icons.Filled.VisibilityOff
        IconButton(onClick = { passwordVisible = !passwordVisible }) {
          Icon(
            imageVector = image,
            contentDescription = null
          )
        }
      },
      textError = when (state.passwordValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = commonR.string.input_blank)
        else -> null
      }
    )
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 2.dp)
        .fillMaxWidth(),
      label = commonR.string.confirm_password,
      value = state.confirmPassword,
      isError = state.confirmPasswordValidation != null &&
        state.confirmPasswordValidation != ErrorValidationType.VALID,
      visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      onChanged = onConfirmPasswordChanged,
      trailingIcon = {
        val image = if (confirmPasswordVisible) Icons.Filled.Visibility
        else Icons.Filled.VisibilityOff
        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
          Icon(
            imageVector = image,
            contentDescription = null
          )
        }
      },
      textError = when (state.confirmPasswordValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = commonR.string.input_blank)
        ErrorValidationType.PASSWORD_NOT_MATCHING ->
          stringResource(id = commonR.string.input_not_match)
        else -> null
      }
    )
    CathConferenceTextField(
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 2.dp)
        .fillMaxWidth(),
      value = state.level.name,
      label = commonR.string.level,
      isError = state.levelValidation != null && state.levelValidation != ErrorValidationType.VALID,
      onChanged = {},
      trailingIcon = {
        IconButton(onClick = {
          activity?.showAsBottomSheet { actionClose ->
            LevelBottomSheet(
              modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp)
                .wrapContentHeight(),
              data = state.listLevelUser,
              onClickItem = onLevelChanged,
              onClose = actionClose
            )
          }
        }) {
          Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null
          )
        }
      },
      isReadOnly = true,
      textError = when (state.levelValidation) {
        ErrorValidationType.EMPTY -> stringResource(id = commonR.string.input_blank)
        else -> null
      }
    )
    Spacer(modifier = Modifier.height(16.dp))
    CathConferenceButton(
      onClick = {
        onClickRegister()
      },
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth(),
      isShowLoading = false,
    ) {
      Text(
        text = stringResource(id = R.string.register),
        style = MaterialTheme.typography.body1,
      )
    }
    Spacer(modifier = Modifier.height(24.dp))
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LevelBottomSheet(
  modifier: Modifier = Modifier,
  data: List<LevelUser>,
  onClickItem: (LevelUser) -> Unit,
  onClose: () -> Unit
) {
  Column(modifier = modifier) {
    data.forEach {
      TextButton(onClick = {
        onClickItem.invoke(it)
        onClose()
      }) {
        Text(
          text = it.name,
          modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
          style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
          )
        )
      }
    }
  }
}

@Preview(name = "Light", showSystemUi = true, showBackground = true)
@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Dark", showSystemUi = true, showBackground = true
)
@Composable
private fun PreviewContent() {
  CCTheme {
    RegisterContent()
  }
}
