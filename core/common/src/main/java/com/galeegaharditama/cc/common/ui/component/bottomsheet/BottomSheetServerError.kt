package com.galeegaharditama.cc.common.ui.component.bottomsheet

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galeegaharditama.cc.common.R
import com.galeegaharditama.cc.common.ui.component.CathConferenceButton
import com.galeegaharditama.cc.common.ui.theme.CCTheme

fun Activity.showBottomSheetServerError(
  autoDissmiss: Boolean = true,
  onRefresh: () -> Unit
) {
  this.showAsBottomSheet(autoDissmiss) { actionClose ->
    Content(
      onClose = actionClose,
      onRefresh = onRefresh
    )
  }
}

@Composable
private fun Content(
  onClose: () -> Unit,
  onRefresh: () -> Unit,
) {
  Surface() {
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
      Image(
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
          .size(200.dp),
        painter = painterResource(id = R.drawable.ic_error), contentDescription = null
      )
      Text(
        modifier = Modifier
          .padding(vertical = 4.dp),
        text = stringResource(id = R.string.popup_error_title),
        style = MaterialTheme.typography.h6,
        color = MaterialTheme.colors.onSurface
      )
      Text(
        modifier = Modifier
          .padding(vertical = 4.dp),
        text = stringResource(id = R.string.popup_error_subtitle),
        style = MaterialTheme.typography.subtitle2,
        color = MaterialTheme.colors.onSurface
      )
      CathConferenceButton(
        onClick = {
          onClose()
          onRefresh.invoke()
        }, modifier = Modifier
          .padding(vertical = 8.dp)
          .fillMaxWidth()
      ) {
        Text(
          text = stringResource(id = R.string.popup_refresh),
          style = MaterialTheme.typography.body1,
          color = MaterialTheme.colors.surface
        )
      }
    }
  }
}

@Preview(name = "Light", showBackground = true)
@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Dark", showBackground = true
)
@Composable
private fun PreviewBottomSheet() {
  CCTheme {
    Content(onClose = {}, onRefresh = {})
  }
}
