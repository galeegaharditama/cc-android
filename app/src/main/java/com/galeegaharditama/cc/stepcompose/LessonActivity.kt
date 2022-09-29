package com.galeegaharditama.cc.stepcompose

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import com.galeegaharditama.cc.stepcompose.lessonfive.LessonFiveActivity
import com.galeegaharditama.cc.stepcompose.lessonsix.LessonSixActivity
import com.galeegaharditama.cc.stepcompose.lessonthree.LessonThreeActivity

@Composable
fun LessonScreen() {
  val context = LocalContext.current
  Column(
    modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    OutlinedButton(onClick = {
      context.startActivity(Intent(context, LessonOneActivity::class.java))
    }) {
      Text("Lesson One")
    }
    OutlinedButton(onClick = {
      context.startActivity(Intent(context, LessonTwoActivity::class.java))
    }) {
      Text("Lesson Two")
    }
    OutlinedButton(onClick = {
      context.startActivity(Intent(context, LessonThreeActivity::class.java))
    }) {
      Text("Lesson Theme")
    }
    OutlinedButton(onClick = {
      context.startActivity(Intent(context, LessonFourActivity::class.java))
    }) {
      Text("Lesson Animation")
    }
    OutlinedButton(onClick = {
      context.startActivity(Intent(context, LessonFiveActivity::class.java))
    }) {
      Text("Lesson State")
    }
    OutlinedButton(onClick = {
      context.startActivity(Intent(context, LessonSixActivity::class.java))
    }) {
      Text("Lesson Navigation")
    }
  }
}

@Preview(showBackground = true, showSystemUi = true, name = "Step")
@Composable
private fun DefaultPreview() {
  CCTheme {
    LessonScreen()
  }
}
