package com.galeegaharditama.cc.stepcompose

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.WbSunny
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galeegaharditama.cc.R
import com.galeegaharditama.cc.common.ui.theme.Amber600
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import com.galeegaharditama.cc.isScrollingUp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LessonFourActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CCTheme {
        LessonFourScreen()
      }
    }
  }
}

@Composable
private fun LessonFourScreen() {
  // The currently selected tab.
  var tabPage by remember { mutableStateOf(TabPage.Home) }
  var isShowMessage by remember { mutableStateOf(false) }
  var isShowLoading by remember { mutableStateOf(false) }
  // Holds the topic that is currently expanded to show its body.
  var expandedTopic by remember { mutableStateOf<String?>(null) }

  // String resources.
  val allTasks = stringArrayResource(R.array.lesson_four_array1)
  val allTopics = stringArrayResource(R.array.lesson_four_array2).toList()
  val backgroundColor by animateColorAsState(
    if (tabPage == TabPage.Home) MaterialTheme.colors.primaryVariant
    else MaterialTheme.colors.secondaryVariant
  )
  val lazyListState = rememberLazyListState()
  val names: List<String> = List(100) { "$it" } // example
  // The coroutine scope for event handlers calling suspend functions.
  val coroutineScope = rememberCoroutineScope()

  // Shows the message about edit feature.
  suspend fun showEditMessage() {
    if (!isShowMessage) {
      isShowMessage = true
      delay(3000L)
      isShowMessage = false
    }
  }

  // Shows loading.
  suspend fun showLoading() {
    if (!isShowLoading) {
      isShowLoading = true
      delay(3000L)
      isShowLoading = false
    }
  }

  LaunchedEffect(Unit) {
    showLoading()
  }

  Scaffold(
    topBar = {
      HomeTopBar(
        backgroundColor = backgroundColor,
        tabPage = tabPage,
        onTabSelected = { tabPage = it }
      )
    },
    backgroundColor = backgroundColor,
    floatingActionButton = {
      HomeFloatingActionButton(
        isExtended = lazyListState.isScrollingUp(),
        onClick = {
          coroutineScope.launch {
            showEditMessage()
          }
        }
      )
    }
  ) { padding ->
    LazyColumn(
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
      state = lazyListState,
      modifier = Modifier.padding(padding)
    ) {
      // weather
      item { Header(title = stringResource(id = R.string.lesson_four_six)) }
      item { Spacer(modifier = Modifier.height(8.dp)) }
      item {
        if (isShowLoading) LoadingRow()
        else WeatherItem {
          coroutineScope.launch {
            showLoading()
          }
        }
      }

      // Topic
      item { Header(title = stringResource(id = R.string.lesson_four_five)) }
      item { Spacer(modifier = Modifier.height(8.dp)) }
      items(allTopics, key = { it }) { topic ->
        TopicItem(
          text = topic,
          expanded = expandedTopic == topic,
          onClick = {
            expandedTopic = if (expandedTopic == topic) null else topic
          }
        )
      }
      item { Spacer(modifier = Modifier.height(8.dp)) }

      items(items = names) {
        GreetingCard(name = it)
      }
    }
    EditMessage(isShow = isShowMessage)
  }
}

@Composable
private fun Header(
  title: String
) {
  Text(
    text = title,
    modifier = Modifier.semantics { heading() },
    style = MaterialTheme.typography.h5
  )
}

@Composable
private fun TopicItem(text: String, expanded: Boolean, onClick: () -> Unit) {
  TopicSpacer(visible = expanded)
  Surface(
    color = MaterialTheme.colors.primary,
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick.invoke() }
      .animateContentSize(),
    elevation = 2.dp
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = Icons.Default.Info, contentDescription = null)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.body1)
      }
      if (expanded) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = stringResource(R.string.lorem_ipsum_long),
          textAlign = TextAlign.Justify
        )
      }
    }
  }
  TopicSpacer(visible = expanded)
}

@Composable
fun TopicSpacer(visible: Boolean) {
  AnimatedVisibility(visible = visible) {
    Spacer(modifier = Modifier.height(8.dp))
  }
}

@Preview(name = "Edit Message", showBackground = true)
@Preview(
  showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
  name = "Edit Message Dark"
)
@Composable
private fun EditMessagePreview() {
  CCTheme {
    WeatherItem {}
  }
}

@Composable
private fun EditMessage(isShow: Boolean) {
  AnimatedVisibility(
    visible = isShow,
    enter = slideInVertically(
      // Enters by sliding in from offset -fullHeight to 0.
      initialOffsetY = { fullHeight -> -fullHeight },
      animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
    ),
    exit = slideOutVertically(
      // Exits by sliding out from offset 0 to -fullHeight.
      targetOffsetY = { fullHeight -> -fullHeight },
      animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
    )
  ) {
    Surface(
      modifier = Modifier.fillMaxWidth(),
      color = MaterialTheme.colors.secondary,
      elevation = 4.dp
    ) {
      Text(
        text = stringResource(id = R.string.lesson_four_four),
        modifier = Modifier.padding(8.dp)
      )
    }
  }
}

@Composable
private fun HomeFloatingActionButton(
  isExtended: Boolean,
  onClick: () -> Unit,
) {
  FloatingActionButton(onClick = onClick) {
    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
      Icon(
        imageVector = Icons.Default.Edit,
        contentDescription = null
      )
      AnimatedVisibility(visible = isExtended) {
        Text(
          text = stringResource(R.string.lesson_four_three),
          modifier = Modifier
            .padding(start = 8.dp, top = 3.dp)
        )
      }
    }
  }
}

private enum class TabPage {
  Home, Work
}

@Composable
private fun HomeTopBar(
  backgroundColor: Color,
  tabPage: TabPage,
  onTabSelected: (tabPage: TabPage) -> Unit
) {
  TabRow(
    selectedTabIndex = tabPage.ordinal,
    backgroundColor = backgroundColor,
    indicator = { tabPositions ->
      HomeTabIndicator(tabPositions, tabPage)
    }
  ) {
    HomeTab(
      icon = Icons.Default.Home,
      title = stringResource(R.string.lesson_four_one),
      onClick = { onTabSelected(TabPage.Home) }
    )
    HomeTab(
      icon = Icons.Default.AccountBox,
      title = stringResource(R.string.lesson_four_two),
      onClick = { onTabSelected(TabPage.Work) }
    )
  }
}

@Composable
private fun HomeTab(
  icon: ImageVector,
  title: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .clickable(onClick = onClick)
      .padding(16.dp),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null
    )
    Spacer(modifier = Modifier.width(16.dp))
    Text(text = title)
  }
}

@Composable
private fun HomeTabIndicator(
  tabPositions: List<TabPosition>,
  tabPage: TabPage
) {
  val transition = updateTransition(
    tabPage,
    label = "Tab indicator"
  )
  val indicatorLeft by transition.animateDp(
    transitionSpec = {
      if (TabPage.Home isTransitioningTo TabPage.Work) {
        // Indicator moves to the right.
        // Low stiffness spring for the left edge so it moves slower than the right edge.
        spring(stiffness = Spring.StiffnessVeryLow)
      } else {
        // Indicator moves to the left.
        // Medium stiffness spring for the left edge so it moves faster than the right edge.
        spring(stiffness = Spring.StiffnessMedium)
      }
    },
    label = "Indicator left"
  ) { page ->
    tabPositions[page.ordinal].left
  }
  val indicatorRight by transition.animateDp(
    transitionSpec = {
      if (TabPage.Home isTransitioningTo TabPage.Work) {
        // Indicator moves to the right
        // Medium stiffness spring for the right edge so it moves faster than the left edge.
        spring(stiffness = Spring.StiffnessMedium)
      } else {
        // Indicator moves to the left.
        // Low stiffness spring for the right edge so it moves slower than the left edge.
        spring(stiffness = Spring.StiffnessVeryLow)
      }
    },
    label = "Indicator right"
  ) { page ->
    tabPositions[page.ordinal].right
  }
  val color by transition.animateColor(
    label = "Border color"
  ) { page ->
    if (page == TabPage.Home) MaterialTheme.colors.background
    else MaterialTheme.colors.secondary
  }
  Box(
    Modifier
      .fillMaxSize()
      .wrapContentSize(align = Alignment.BottomStart)
      .offset(x = indicatorLeft)
      .width(indicatorRight - indicatorLeft)
      .padding(4.dp)
      .fillMaxSize()
      .border(
        BorderStroke(2.dp, color),
        RoundedCornerShape(4.dp)
      )
  )
}

@Composable
private fun WeatherItem(onRefresh: () -> Unit) {
  Row(
    modifier = Modifier
      .heightIn(min = 64.dp)
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = Icons.Default.WbSunny,
      contentDescription = null,
      modifier = Modifier.size(48.dp),
      tint = Amber600
    )
    Spacer(modifier = Modifier.width(16.dp))
    Text(text = "18 dor", style = MaterialTheme.typography.body2)
    Spacer(modifier = Modifier.weight(1f))
    IconButton(onClick = onRefresh) {
      Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = null
      )
    }
  }
}

@Composable
private fun LoadingRow() {
  // Creates an `InfiniteTransition` that runs infinite child animation values.
  val infiniteTransition = rememberInfiniteTransition()
  val alpha by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    // `infiniteRepeatable` repeats the specified duration-based `AnimationSpec` infinitely.
    animationSpec = infiniteRepeatable(
      // The `keyframes` animates the value by specifying multiple timestamps.
      animation = keyframes {
        // One iteration is 1000 milliseconds.
        durationMillis = 1000
        // 0.7f at the middle of an iteration.
        0.7f at 500
      },
      // When the value finishes animating from 0f to 1f, it repeats by reversing the
      // animation direction.
      repeatMode = RepeatMode.Reverse
    )
  )
  Row(
    modifier = Modifier
      .heightIn(min = 64.dp)
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(48.dp)
        .clip(CircleShape)
        .background(Color.LightGray.copy(alpha = alpha))
    )
    Spacer(modifier = Modifier.width(16.dp))
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(32.dp)
        .background(Color.LightGray.copy(alpha = alpha))
    )
  }
}
