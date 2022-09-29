package com.galeegaharditama.cc.common.ui.component.bottomsheet

import android.app.Activity
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

fun Activity.showAsBottomSheet(
  autoDissmiss: Boolean = true,
  content: @Composable (() -> Unit) -> Unit
) {
  val viewGroup = this.findViewById(android.R.id.content) as ViewGroup
  addContentToView(viewGroup, autoDissmiss, content)
}

fun Fragment.showAsBottomSheet(
  autoDissmiss: Boolean = true,
  content: @Composable (() -> Unit) -> Unit
) {
  val viewGroup = requireActivity().findViewById(android.R.id.content) as ViewGroup
  addContentToView(viewGroup, autoDissmiss, content)
}

private fun addContentToView(
  viewGroup: ViewGroup,
  autoDissmiss: Boolean = true,
  content: @Composable (() -> Unit) -> Unit
) {
  viewGroup.addView(
    ComposeView(viewGroup.context).apply {
      setContent {
        BottomSheetWrapper(viewGroup, this, autoDissmiss, content)
      }
    }
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetWrapper(
  parent: ViewGroup,
  composeView: ComposeView,
  autoDissmiss: Boolean = true,
  content: @Composable (() -> Unit) -> Unit
) {
  val coroutineScope = rememberCoroutineScope()
  val modalBottomsheetState = rememberModalBottomSheetState(
    ModalBottomSheetValue.Hidden,
    confirmStateChange = {
      if (autoDissmiss) it != ModalBottomSheetValue.HalfExpanded
      else false
    }
  )
  var isSheetOpened by remember { mutableStateOf(false) }

  CCTheme {
    ModalBottomSheetLayout(
      sheetBackgroundColor = Color.Transparent,
      sheetState = modalBottomsheetState,
      sheetContent = {
        BottomSheetUIWrapper {
          content { animateHideBottomSheet(coroutineScope, modalBottomsheetState) }
        }
      }
    ) {}
  }

  BackHandler {
    animateHideBottomSheet(coroutineScope, modalBottomsheetState)
  }

  LaunchedEffect(modalBottomsheetState.currentValue) {
    when (modalBottomsheetState.currentValue) {
      ModalBottomSheetValue.Hidden -> {
        when {
          isSheetOpened -> parent.removeView(composeView)
          else -> {
            isSheetOpened = true
            modalBottomsheetState.show()
          }
        }
      }
      else -> Timber.i("Bottom sheet " + modalBottomsheetState.currentValue + " state")
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
private fun animateHideBottomSheet(
  coroutineScope: CoroutineScope,
  modalBottomSheetState: ModalBottomSheetState
) {
  coroutineScope.launch {
    modalBottomSheetState.hide()
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetUIWrapper(
  content: @Composable () -> Unit
) {
  Box(
    modifier = Modifier
      .wrapContentHeight()
      .fillMaxWidth()
      .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
      .background(MaterialTheme.colors.surface)
  ) {
    Box(modifier = Modifier.padding(top = 25.dp)) {
      content()
    }

    Divider(
      color = Color.Gray,
      thickness = 5.dp,
      modifier = Modifier
        .padding(top = 15.dp)
        .align(Alignment.TopCenter)
        .width(50.dp)
        .clip(RoundedCornerShape(50.dp))
    )
  }
}
