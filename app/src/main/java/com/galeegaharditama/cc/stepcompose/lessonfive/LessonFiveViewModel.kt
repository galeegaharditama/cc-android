package com.galeegaharditama.cc.stepcompose.lessonfive

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel

class LessonFiveViewModel : ViewModel() {
  private val _tasks = fakeTasks().toMutableStateList()
  val tasks: List<Task>
    get() = _tasks

  fun remove(item: Task) {
    _tasks.remove(item)
  }

  fun isTaskChecked(item: Task, isChecked: Boolean) {
    _tasks.find { it.id == item.id }?.let { it.isChecked.value = isChecked }
  }
}
