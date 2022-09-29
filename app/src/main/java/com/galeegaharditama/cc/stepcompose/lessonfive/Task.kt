package com.galeegaharditama.cc.stepcompose.lessonfive

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Task(
  val id: Int,
  val label: String,
  val isChecked: MutableState<Boolean> = mutableStateOf(false)
)

fun fakeTasks() = List(50) { Task(id = it, label = "Task $it") }
