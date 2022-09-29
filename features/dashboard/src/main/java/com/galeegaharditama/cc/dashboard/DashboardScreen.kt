package com.galeegaharditama.cc.dashboard

import android.app.Activity
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galeegaharditama.cc.common.handleEffect
import com.galeegaharditama.cc.common.ui.component.CardExpandable
import com.galeegaharditama.cc.common.ui.component.TopBar
import com.galeegaharditama.cc.common.ui.component.bottomsheet.showBottomSheetLostConnection
import com.galeegaharditama.cc.common.ui.component.bottomsheet.showBottomSheetServerError
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import com.galeegaharditama.cc.dashboard.model.DashboardItemDataView
import com.galeegaharditama.cc.domain.model.Approval
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel

@Composable
fun DashboardScreen(
  activity: Activity? = null,
  onSuccessLogout: () -> Unit,
  onClickViewAll: () -> Unit,
  onClickItem: (Int) -> Unit,
  onClickAdd: () -> Unit
) {
  val viewModel = getViewModel<DashboardViewModel>()
  val state = viewModel.state.collectAsState()

  val context = LocalContext.current

  viewModel.handleEffect {
    when (it) {
      is DashboardEffect.ErrorGetData -> {
        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
      }
      is DashboardEffect.SuccessLogout -> {
        onSuccessLogout()
      }
      is DashboardEffect.ServerError -> activity?.showBottomSheetServerError {
        viewModel.dispatch(DashboardAction.Refresh)
      }
      is DashboardEffect.LostConnection -> activity?.showBottomSheetLostConnection {
        viewModel.dispatch(DashboardAction.Refresh)
      }
    }
  }

  DashboardContent(
    onLogout = { viewModel.dispatch(DashboardAction.ClickLogout) },
    onClickViewAll = onClickViewAll,
    state = state.value,
    onClickItem = onClickItem,
    onClickAdd = onClickAdd,
    onRefresh = { viewModel.dispatch(DashboardAction.Refresh) }
  )
}

@Composable
private fun DashboardContent(
  state: DashboardState = DashboardState(),
  onRefresh: () -> Unit,
  onLogout: () -> Unit,
  onClickViewAll: () -> Unit,
  onClickItem: (Int) -> Unit,
  onClickAdd: () -> Unit,
) {
  var refresh by rememberSaveable { mutableStateOf(false) }
  if (refresh) {
    refresh = state.isLoadingContent
  }
  Scaffold(
    topBar = {
      TopBarContent(onClickLogout = onLogout)
    },
    floatingActionButton = { FloatingAction(onClickAdd = onClickAdd) },
  ) { innerPadding ->
    SwipeRefresh(
      state = rememberSwipeRefreshState(refresh),
      onRefresh = {
        refresh = true
        onRefresh.invoke()
      },
    ) {
      LazyColumn(contentPadding = innerPadding) {
        item {
          SectionTitle(
            onClickViewAll = onClickViewAll,
            modifier = Modifier.padding(horizontal = 16.dp)
          )
        }
        if (state.isLoadingContent) {
          items(4) {
            DashboardLoadingContent(
              modifier =
              Modifier.padding(horizontal = 16.dp)
            )
          }
        } else
          items(state.listCathConference) { item ->
            ItemCathConference(
              modifier = Modifier.padding(horizontal = 16.dp),
              dataView = item,
              onClickItem = onClickItem
            )
          }
      }
    }
  }
}

@Composable
private fun TopBarContent(
  onClickLogout: () -> Unit,
) {
  TopBar(
    title = stringResource(id = R.string.dashboard_title),
    actions = {
      IconButton(
        onClick = onClickLogout,
        modifier = Modifier.align(Alignment.Bottom)
      ) {
        Icon(
          imageVector = Icons.Filled.Logout,
          contentDescription = null,
        )
      }
    }
  )
}

@Composable
private fun FloatingAction(
  onClickAdd: () -> Unit
) {
  FloatingActionButton(
    onClick = onClickAdd,
    backgroundColor = MaterialTheme.colors.primary,
  ) {
    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
  }
}

@Composable
private fun SectionTitle(
  modifier: Modifier = Modifier,
  onClickViewAll: () -> Unit
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = stringResource(id = R.string.data_cc),
      style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
    )
    Spacer(modifier = Modifier.weight(1f))
    TextButton(onClick = onClickViewAll) {
      Text(
        text = stringResource(id = R.string.view_all),
        style = MaterialTheme.typography.caption,
      )
    }
  }
}

@Composable
private fun ItemCathConference(
  modifier: Modifier = Modifier,
  dataView: DashboardItemDataView,
  onClickItem: (Int) -> Unit
) {
  var isExpanded by rememberSaveable { mutableStateOf(false) }

  CardExpandable(
    modifier = modifier
      .padding(vertical = 4.dp)
      .clickable {
        onClickItem.invoke(dataView.id)
      },
    modifierColumn = Modifier.padding(12.dp)
  ) {
    ItemTitle(dataView = dataView)

    ItemDetail(
      dataView = dataView,
      isExpand = isExpanded,
      onClickExpand = { isExpanded = it }
    )
  }
}

@Composable
private fun ItemTitle(
  dataView: DashboardItemDataView
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      text = dataView.rekamMedik,
      style = MaterialTheme.typography.h6.copy(
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic
      ),
      maxLines = 2,
      overflow = TextOverflow.Ellipsis
    )
    Spacer(modifier = Modifier.weight(1f))
    if (dataView.approval?.slug?.isNotBlank() == true) {
      Text(
        text = dataView.approval.name,
        style = MaterialTheme.typography.caption.copy(
          color = MaterialTheme.colors.secondary,
          fontStyle = FontStyle.Italic
        )
      )
    }
  }
}

@Composable
private fun ItemDetail(
  dataView: DashboardItemDataView,
  isExpand: Boolean,
  onClickExpand: (Boolean) -> Unit
) {
  Row {
    Column(modifier = Modifier.weight(1f)) {
      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = com.galeegaharditama.cc.common.R.string.tgl_cc)
      )
      ItemValue(text = dataView.tglCathConference)

      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = com.galeegaharditama.cc.common.R.string.dpjp_utama)
      )
      ItemValue(text = dataView.dpjpUtama)

      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = com.galeegaharditama.cc.common.R.string.dpjp_tindakan)
      )
      ItemValue(text = dataView.dpjpTindakan)
    }
    Column(modifier = Modifier.weight(1f)) {
      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = com.galeegaharditama.cc.common.R.string.patient_name)
      )
      ItemValue(text = dataView.name, maxlines = 1)

      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = com.galeegaharditama.cc.common.R.string.patient_age)
      )
      ItemValue(
        text = stringResource(
          id = com.galeegaharditama.cc.common.R.string.count_age,
          "${dataView.umur}"
        )
      )

      ItemLabel(
        modifier = Modifier.padding(vertical = 8.dp),
        text = stringResource(id = com.galeegaharditama.cc.common.R.string.patient_religion)
      )
      ItemValue(text = dataView.agama)
    }
    IconButton(
      onClick = { onClickExpand.invoke(!isExpand) },
      modifier = Modifier.align(Alignment.CenterVertically)
    ) {
      Icon(
        imageVector = if (isExpand) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
        contentDescription = null
      )
    }
  }
  if (isExpand) {
    ItemLabel(
      modifier = Modifier.padding(vertical = 8.dp),
      text = stringResource(id = com.galeegaharditama.cc.common.R.string.diagnosis_result)
    )
    ItemValue(
      modifier = Modifier.padding(bottom = 8.dp),
      text = dataView.diagnosis, maxlines = 3
    )
  }
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

@Composable
private fun ItemValue(modifier: Modifier = Modifier, text: String, maxlines: Int = 2) {
  Text(
    text = text,
    style = MaterialTheme.typography.subtitle2.copy(
      fontWeight = FontWeight.Light
    ),
    modifier = modifier.padding(start = 8.dp),
    maxLines = maxlines,
    overflow = TextOverflow.Ellipsis
  )
}

@Preview(name = "Light", showBackground = true)
@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Dark", showBackground = true
)
@Composable
private fun PreviewContent() {
  CCTheme {
    DashboardContent(
      onRefresh = {},
      onLogout = {},
      onClickViewAll = {},
      onClickItem = {},
      onClickAdd = {}
    )
  }
}

@Preview(name = "Light", showBackground = true)
@Preview(
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Dark", showBackground = true
)
@Composable
private fun PreviewItemContent() {
  CCTheme {
    ItemCathConference(
      dataView = DashboardItemDataView(
        0,
        "Briandhika Galuh Rizky Tami Andamore",
        "No Rekam Medik",
        24,
        "wadidaw",
        "tgl CC",
        "tgl Action",
        "diagnosis",
        "Dr. Galih Gaharditama Andamore, Sp. Ok",
        "dpjp tindakan",
        Approval("tunda", "Tunda"),
      ),
      onClickItem = {}
    )
  }
}
