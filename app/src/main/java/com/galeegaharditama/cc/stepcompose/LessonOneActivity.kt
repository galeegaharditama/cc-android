package com.galeegaharditama.cc.stepcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galeegaharditama.cc.R
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import com.galeegaharditama.cc.common.ui.theme.md_theme_light_primaryContainer

class LessonOneActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CCTheme {
        // A surface container using the 'background' color from the theme
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colors.background
        ) {
          LessonOneScreen()
        }
      }
    }
  }
}

@Composable
private fun LessonOneScreen() {
  var shouldShowOnBoard by rememberSaveable { mutableStateOf(true) }
  val names: List<String> = List(100) { "$it" }

  if (shouldShowOnBoard) {
    OnBoardingScreen(onContinueClicked = { shouldShowOnBoard = false })
  } else LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
    items(items = names, key = { it }) {
      GreetingCard(name = it)
    }
  }
}

@Composable
fun GreetingCard(name: String) {
  Card(
    backgroundColor = MaterialTheme.colors.primary,
    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    CardContent(name = name)
  }
}

@Composable
private fun CardContent(name: String) {
  var isExpanded by rememberSaveable { mutableStateOf(false) }

  Row(
    modifier = Modifier
      .padding(12.dp)
      .animateContentSize(
        animationSpec = spring(
          dampingRatio = Spring.DampingRatioMediumBouncy,
          stiffness = Spring.StiffnessLow
        )
      ),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(
      modifier = Modifier
        .weight(1f)
        .padding(24.dp)
    ) {
      Row {
        Text(text = "Hello ")
        Text(
          text = name,
          style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
          modifier = Modifier.padding(start = 16.dp)
        )
      }
      if (isExpanded) Text(text = stringResource(id = R.string.lorem_ipsum_long))
    }
    IconButton(onClick = { isExpanded = !isExpanded }) {
      Icon(
        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
        contentDescription =
        if (isExpanded) stringResource(id = R.string.show_less)
        else stringResource(id = R.string.show_more)
      )
    }
  }
}

@Composable
private fun Greeting(name: String) {
  var isExpanded by rememberSaveable { mutableStateOf(false) }
  val extraPadding by animateDpAsState(if (isExpanded) 48.dp else 0.dp)
  Surface(
    color = MaterialTheme.colors.primary,
    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
  ) {
    Row(modifier = Modifier.padding(24.dp)) {
      Row(
        modifier = Modifier
          .weight(1f)
          .padding(bottom = extraPadding)
      ) {
        Text(text = "Hello ")
        Text(
          text = name,
          style = MaterialTheme.typography.h4.copy(
            fontWeight = FontWeight.Bold
          ),
          modifier = Modifier.padding(start = 16.dp)
        )
        if (isExpanded) Text(text = stringResource(id = R.string.lorem_ipsum_long))
      }
      /*Column(
          modifier = Modifier
              .weight(1f)
              .padding(bottom = extraPadding)
      ) {
          Text(text = "Hello, ")
          Text(text = name, style = MaterialTheme.typography.h4)
      }*/
      OutlinedButton(onClick = {
        isExpanded = !isExpanded
      }) {
        if (isExpanded) Text(text = "Show less")
        else Text(text = "Show more")
      }
    }
  }
}

@Composable
private fun OnBoardingScreen(onContinueClicked: () -> Unit) {
  Surface {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(text = "Welcome Learn Compose")
      Button(
        onClick = onContinueClicked,
        modifier = Modifier.padding(vertical = 24.dp)
      ) {
        Text(text = "Continue")
      }
    }
  }
}

@Preview(showBackground = true, name = "Light Mode Card")
@Preview(
  showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Dark Mode Card"
)
@Composable
private fun CardContent() {
  GreetingCard(name = "Sapi")
}

@Preview(showBackground = true, showSystemUi = true, name = "Light Mode")
@Preview(
  showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Dark Mode"
)
@Composable
private fun Greeting() {
  Greeting(name = "Sapi")
}

@Preview(showBackground = true, showSystemUi = true, name = "Light Mode")
@Preview(
  showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Dark Mode"
)
@Composable
private fun Greeting1() {
  Surface(
    modifier = Modifier.fillMaxSize()
  ) {
    Box(
      modifier = Modifier
        .height(180.dp)
        .background(color = md_theme_light_primaryContainer)
    ) {
    }
  }
}

@Preview(showBackground = true, showSystemUi = true, name = "Onboard")
@Composable
private fun DefaultPreview() {
  CCTheme {
    OnBoardingScreen {}
  }
}
