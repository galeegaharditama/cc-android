package com.galeegaharditama.cc.common.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CathConferenceTextField(
  modifier: Modifier = Modifier,
  label: Int,
  value: String,
  isError: Boolean,
  isEnabled: Boolean = true,
  isReadOnly: Boolean = false,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  visualTransformation: VisualTransformation = VisualTransformation.None,
  textStyle: TextStyle? = null,
  trailingIcon: @Composable (() -> Unit)? = null,
  textError: String? = null,
  onChanged: ((String) -> Unit)? = null,
) {
  OutlinedTextField(
    modifier = modifier,
    value = value,
    label = { Text(stringResource(id = label)) },
    onValueChange = onChanged ?: {},
    readOnly = isReadOnly,
    textStyle = textStyle ?: MaterialTheme.typography.body2.copy(fontSize = 18.sp),
    keyboardOptions = keyboardOptions,
    visualTransformation = visualTransformation,
    isError = isError,
    enabled = isEnabled,
    trailingIcon = trailingIcon
  )
  textError?.let {
    TextError(value = it, modifier = Modifier.padding(horizontal = 20.dp))
  }
}

@Composable
fun CathConferenceFileUpload(
  label: String,
  modifier: Modifier = Modifier,
  textButton: String = "Tambah File",
  isError: Boolean = false,
  textError: String? = null,
  onClickBtn: () -> Unit,
  itemsContent: @Composable () -> Unit,
) {
  val colorBorder = if (isError) MaterialTheme.colors.error else Color.Gray

  Box(
    modifier = modifier
      .border(1.dp, colorBorder, RoundedCornerShape(CornerSize(5.dp)))
  ) {
    Column {
      Text(
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .padding(top = 8.dp)
          .fillMaxWidth(),
        text = label,
        style = MaterialTheme.typography.subtitle1.copy(
          fontSize = 16.sp, fontWeight = FontWeight.Light,
          color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
      )
      textError?.let {
        TextError(
          value = it,
          modifier = Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 20.dp)
        )
      }
      Spacer(modifier = Modifier.height(8.dp))
      itemsContent()
      Spacer(modifier = Modifier.height(8.dp))
      OutlinedButton(
        onClick = onClickBtn,
        modifier = Modifier
          .padding(horizontal = 8.dp, vertical = 8.dp)
          .fillMaxWidth()
      ) {
        Text(text = textButton)
      }
    }
  }
}
