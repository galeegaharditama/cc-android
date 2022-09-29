package com.galeegaharditama.cc.login

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galeegaharditama.cc.common.handleEffect
import com.galeegaharditama.cc.common.ui.component.CathConferenceButton
import com.galeegaharditama.cc.common.ui.component.CathConferenceTextField
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import com.galeegaharditama.cc.common.ui.theme.md_theme_dark_outline
import com.galeegaharditama.cc.common.ui.theme.md_theme_light_outline
import com.galih.library.validation.ErrorValidationType
import com.onesignal.OneSignal
import org.koin.androidx.compose.getViewModel
import timber.log.Timber
import com.galeegaharditama.cc.common.R as commonR

@Composable
fun LoginScreen(
  onSuccessLogin: () -> Unit,
  onClickForgot: () -> Unit,
  onClickRegister: () -> Unit
) {
  val viewModel = getViewModel<LoginViewModel>()

  val token = OneSignal.getDeviceState()?.userId
  viewModel.dispatch(LoginAction.TokenGenerate(token ?: ""))

  val context = LocalContext.current

  viewModel.handleEffect {
    when (it) {
      is LoginEffect.SuccessLogin -> {
        onSuccessLogin.invoke()
      }
      is LoginEffect.ShowErrorMessage -> {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
      }
    }
  }

  val state = viewModel.state.collectAsState()

  LoginContent(
    state = state.value,
    onEmailChanged = { viewModel.dispatch(LoginAction.ChangeEmail(it)) },
    onPasswordChanged = { viewModel.dispatch(LoginAction.ChangePassword(it)) },
    onClickLogin = { viewModel.dispatch(LoginAction.ClickLogin) },
    onClickForgot = onClickForgot,
    onClickRegister = onClickRegister
  )
}

@Composable
private fun LoginContent(
  state: LoginState,
  onEmailChanged: (String) -> Unit,
  onPasswordChanged: (String) -> Unit,
  onClickLogin: () -> Unit,
  onClickForgot: () -> Unit,
  onClickRegister: () -> Unit
) {
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
    LazyColumn(modifier = Modifier.fillMaxSize()) {
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
        Form(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          state = state,
          onEmailChanged = onEmailChanged,
          onPasswordChanged = onPasswordChanged,
          onClickLogin = onClickLogin,
          onClickForgot = onClickForgot,
          onClickRegister = onClickRegister
        )
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
    text = stringResource(id = R.string.login_app_name),
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

@Composable
private fun Form(
  modifier: Modifier = Modifier,
  state: LoginState,
  onEmailChanged: (String) -> Unit,
  onPasswordChanged: (String) -> Unit,
  onClickLogin: () -> Unit,
  onClickForgot: () -> Unit,
  onClickRegister: () -> Unit
) {
  Timber.e("$state")
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  val focusManager = LocalFocusManager.current
  Card(modifier = modifier, elevation = 8.dp) {
    Column {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(id = R.string.login_title),
        style = MaterialTheme.typography.h4.copy(
          fontSize = 20.sp,
          fontWeight = FontWeight.Light,
          color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
        ),
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        textAlign = TextAlign.Center
      )
      Text(
        text = stringResource(id = R.string.login_subtitle),
        style = MaterialTheme.typography.h5.copy(
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
        label = commonR.string.email,
        value = state.email,
        isError = state.emailValidation != null && state.emailValidation != ErrorValidationType.VALID,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        textError = when (state.emailValidation) {
          ErrorValidationType.EMPTY -> stringResource(id = commonR.string.input_blank)
          ErrorValidationType.INVALID_FORMAT -> stringResource(id = commonR.string.email_invalid)
          else -> null
        },
        onChanged = onEmailChanged
      )
      CathConferenceTextField(
        modifier = Modifier
          .padding(horizontal = 16.dp, vertical = 2.dp)
          .fillMaxWidth(),
        label = commonR.string.password,
        value = state.password,
        isError = state.passwordValidation != null && state.passwordValidation != ErrorValidationType.VALID,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        textError = when (state.passwordValidation) {
          ErrorValidationType.EMPTY -> stringResource(id = commonR.string.input_blank)
          else -> null
        },
        onChanged = onPasswordChanged,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
          val image = if (passwordVisible) Icons.Filled.Visibility
          else Icons.Filled.VisibilityOff
          IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(
              imageVector = image,
              contentDescription = null
            )
          }
        }
      )
      Spacer(modifier = Modifier.height(16.dp))
      CathConferenceButton(
        onClick = {
          focusManager.clearFocus()
          onClickLogin()
        },
        modifier = Modifier
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .fillMaxWidth(),
        isShowLoading = true,
        isLoading = state.isLoading,
      ) {
        Text(
          text = stringResource(id = R.string.login),
          style = MaterialTheme.typography.body1,
        )
      }
      Spacer(modifier = Modifier.height(24.dp))
      val context = LocalContext.current
      val annotedForgotPassword = buildAnnotatedString {
        val str = stringResource(id = R.string.forgot_password)
        append(str)
        addStringAnnotation(
          tag = "route",
          annotation = "forgot",
          start = 0,
          end = str.length - 1
        )
      }
      ClickableText(
        style = MaterialTheme.typography.h6.copy(
          textAlign = TextAlign.Center,
          fontWeight = FontWeight.Light,
          fontSize = 16.sp,
          color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
        ),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        text = annotedForgotPassword,
        onClick = {
          annotedForgotPassword.getStringAnnotations(it, it).firstOrNull()
            ?.let { stringAnnotation ->
              onClickForgot.invoke()
              println("🔥 Clicked: $it, item: ${stringAnnotation.item}")
              Toast.makeText(
                context,
                "${stringAnnotation.item} $stringAnnotation",
                Toast.LENGTH_SHORT
              ).show()
            }
        }
      )
      val annotatedRegister = buildAnnotatedString {
        val str = stringResource(id = R.string.register_here)
        val startIndex = str.indexOf("disini")
        val endIndex = startIndex + 6
        append(str)

        addStyle(
          style = SpanStyle(
            color = Color(0xff64B5F6),
            fontSize = 18.sp,
            textDecoration = TextDecoration.Underline
          ),
          start = startIndex, end = endIndex
        )

        // attach a string annotation that stores a URL to the text "Jetpack Compose".
        addStringAnnotation(
          tag = "route",
          annotation = "register",
          start = startIndex,
          end = endIndex
        )
      }
      ClickableText(
        style = MaterialTheme.typography.h6.copy(
          textAlign = TextAlign.Center,
          fontWeight = FontWeight.Light,
          fontSize = 16.sp,
          color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
        ),
        text = annotatedRegister,
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 16.dp),
        onClick = {
          annotatedRegister.getStringAnnotations(it, it)
            .firstOrNull()?.let { onClickRegister.invoke() }
        }
      )
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
    LoginContent(
      state = LoginState(),
      onEmailChanged = { },
      onPasswordChanged = { },
      onClickRegister = {},
      onClickForgot = {},
      onClickLogin = {}
    )
  }
}
