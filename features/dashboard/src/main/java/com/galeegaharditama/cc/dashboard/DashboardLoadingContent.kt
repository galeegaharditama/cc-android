package com.galeegaharditama.cc.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galeegaharditama.cc.common.R
import com.galeegaharditama.cc.common.ui.component.CardExpandable
import com.galeegaharditama.cc.common.ui.component.LoadingShimmer
import com.galeegaharditama.cc.common.ui.theme.CCTheme

@Composable
fun DashboardLoadingContent(
  modifier: Modifier = Modifier
) {
  CardExpandable(
    modifier = modifier
      .padding(vertical = 4.dp),
    modifierColumn = Modifier.padding(12.dp)
  ) {
    ItemTitle()

    ItemDetail()
  }
}

@Composable
private fun ItemTitle() {
  Row(verticalAlignment = Alignment.CenterVertically) {
    LoadingShimmer(
      modifier = Modifier
        .height(25.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth(fraction = 0.7f)
    )
    Spacer(modifier = Modifier.weight(1f))
    LoadingShimmer(
      modifier = Modifier
        .height(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth(fraction = 0.5f)
    )
  }
}

@Composable
private fun ItemDetail() {
  Row {
    Column(modifier = Modifier.weight(1f)) {
      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = R.string.tgl_cc)
      )
      ShimmerValue()

      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = R.string.dpjp_utama)
      )
      ShimmerValue()


      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = R.string.dpjp_tindakan)
      )
      ShimmerValue()

    }
    Column(modifier = Modifier.weight(1f)) {
      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = R.string.patient_name)
      )
      ShimmerValue()


      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = R.string.patient_age)
      )
      ShimmerValue()

      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = R.string.patient_religion)
      )
      ShimmerValue()

    }
    IconButton(
      onClick = { },
      modifier = Modifier.align(Alignment.CenterVertically)
    ) {
      Icon(
        Icons.Filled.ExpandMore,
        contentDescription = null
      )
    }
  }
}

@Composable
private fun ShimmerValue() {
  LoadingShimmer(
    modifier = Modifier
      .height(15.dp)
      .clip(RoundedCornerShape(10.dp))
      .fillMaxWidth(0.7f)
  )
}

@Composable
private fun ItemLabel(modifier: Modifier = Modifier, text: String) {
  Text(
    modifier = modifier,
    text = text,
    style = MaterialTheme.typography.subtitle2.copy(
      fontWeight = FontWeight.Bold
    )
  )
}

@Preview
@Composable
private fun PreviewShimmer() {
  CCTheme {
    DashboardLoadingContent()
  }
}
