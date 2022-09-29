package com.galeegaharditama.cc.stepcompose.lessonfive

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.galeegaharditama.cc.common.ui.theme.CCTheme

class LessonFiveActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CCTheme {
        StateScreen()
      }
    }
  }
}

@Composable
private fun StateScreen(
  viewModel: LessonFiveViewModel = viewModel()
) {
  var count by rememberSaveable { mutableStateOf(0) }

  LazyColumn(modifier = Modifier.padding(8.dp)) {
    item {
      WaterCount(
        counter = count,
        modifier = Modifier.padding(8.dp),
        onIncrement = { count++ },
        onClear = { count = 0 }
      )
    }
    items(
      count = viewModel.tasks.size,
      key = { viewModel.tasks[it].id },
      itemContent = { index ->
        val task = viewModel.tasks[index]
        TaskItem(
          task = task,
          onChecked = { item, isChecked -> viewModel.isTaskChecked(item, isChecked) },
          onRemove = {
            viewModel.remove(it)
          }
        )
      }
    )
  }
}

@Composable
private fun WaterCount(
  counter: Int,
  modifier: Modifier = Modifier,
  onIncrement: () -> Unit = {},
  onClear: () -> Unit = {}
) {
  Column(modifier = modifier.fillMaxWidth()) {
    if (counter > 0) Text(
      text = "You've had $counter glass",
      color = MaterialTheme.colors.onSurface,
      modifier = Modifier.padding(vertical = 8.dp),
      style = MaterialTheme.typography.body1
    )
    Row {
      OutlinedButton(onClick = onIncrement, enabled = counter < 10) {
        Text(text = "Add One")
      }
      Spacer(modifier = Modifier.width(8.dp))
      if (counter > 0)
        OutlinedButton(onClick = onClear) {
          Text(text = "Clear")
        }
    }
  }
}

@Composable
private fun TaskItem(
  task: Task,
  modifier: Modifier = Modifier,
  onChecked: (Task, Boolean) -> Unit,
  onRemove: (Task) -> Unit
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp)
  ) {
    Text(
      text = task.label,
      color = MaterialTheme.colors.onSurface,
      style = MaterialTheme.typography.body2,
      modifier = Modifier.weight(1f),
    )
    Checkbox(checked = task.isChecked.value, onCheckedChange = { onChecked.invoke(task, it) })
    IconButton(onClick = { onRemove.invoke(task) }) {
      Icon(
        imageVector = Icons.Default.Delete,
        contentDescription = null,
        tint = MaterialTheme.colors.onSurface.copy(alpha = 0.8f)
      )
    }
  }
}

@Preview(name = "Light Screen")
@Preview(name = "Dark Screen", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScreenPreview() {
  CCTheme {
//        TaskItem(
//            Task(1, "test"),
//            onChecked = { _, _ -> },
//            onRemove = {}
//        )
    StateScreen()
  }
}
