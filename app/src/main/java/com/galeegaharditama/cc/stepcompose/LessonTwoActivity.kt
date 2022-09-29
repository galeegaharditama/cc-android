package com.galeegaharditama.cc.stepcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.galeegaharditama.cc.R
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import com.galeegaharditama.cc.common.R as commonR

class LessonTwoActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CCTheme {
        LessonTwoScreen()
      }
    }
  }
}

@Composable
private fun LessonTwoScreen() {
  Scaffold(
    bottomBar = { MyAppBottomNavigation() }
  ) { padding ->
    HomeScreen(Modifier.padding(padding))
  }
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
  Column(
    modifier
      .padding(8.dp)
      .verticalScroll(rememberScrollState())
  ) {
    Spacer(Modifier.height(16.dp))
    SearchBar(Modifier.padding(horizontal = 16.dp))
    HomeSection(title = R.string.lesson_two_section_one) {
      AlignYourBodyRow()
    }
    HomeSection(title = R.string.lesson_two_section_two) {
      FavoriteCollectionsGrid()
    }
    Spacer(Modifier.height(16.dp))
  }
}

@Composable
private fun HomeSection(
  @StringRes title: Int,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Column(modifier) {
    Text(
      text = stringResource(title).uppercase(),
      style = MaterialTheme.typography.h2,
      modifier = modifier
        .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
        .padding(horizontal = 16.dp)
    )
    content()
  }
}

@Composable
private fun SearchBar(modifier: Modifier = Modifier) {
  TextField(
    value = "", onValueChange = {},
    modifier = modifier
      .fillMaxWidth()
      .heightIn(min = 56.dp),
    leadingIcon = {
      Icon(imageVector = Icons.Default.Search, contentDescription = null)
    },
    colors = TextFieldDefaults.textFieldColors(
      backgroundColor = MaterialTheme.colors.surface
    ),
    placeholder = {
      Text(stringResource(R.string.lesson_two_placeholder))
    },
  )
}

@Composable
private fun AlignYourBodyRow(
  modifier: Modifier = Modifier
) {
  val alignYourBodyData = listOf(
    Data(commonR.drawable.logo_univ, "Univ"),
    Data(commonR.drawable.logo_rs, "RS"),
    Data(commonR.drawable.logo_idi, "IDI"),
    Data(commonR.drawable.logo_perki, "perki"),
    Data(R.drawable.ic_doctor_p, "dokter"),
  )
  LazyRow(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    contentPadding = PaddingValues(horizontal = 16.dp),
    modifier = modifier
  ) {
    items(alignYourBodyData) { item ->
      AlignYourBodyElement(item.drawable, item.text)
    }
  }
}

data class Data(
  val drawable: Int,
  val text: String
)

@Composable
private fun AlignYourBodyElement(
  @DrawableRes drawable: Int,
  text: String,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(id = drawable),
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .size(88.dp)
        .padding(8.dp)
        .clip(RectangleShape)
    )
    Text(
      text = text,
      style = MaterialTheme.typography.h3,
      modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp)
    )
  }
}

@Composable
private fun FavoriteCollectionsGrid(
  modifier: Modifier = Modifier
) {
  val alignYourBodyData = listOf(
    Data(commonR.drawable.logo_univ, "Univ"),
    Data(commonR.drawable.logo_rs, "RS"),
    Data(commonR.drawable.logo_idi, "IDI"),
    Data(commonR.drawable.logo_perki, "perki"),
    Data(R.drawable.ic_doctor_p, "dokter"),
  ).shuffled()
  LazyHorizontalGrid(
    rows = GridCells.Fixed(2),
    contentPadding = PaddingValues(horizontal = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    modifier = modifier.height(120.dp)
  ) {
    items(alignYourBodyData) { item ->
      FavoriteCollectionCard(
        image = item.drawable,
        text = item.text,
      )
    }
  }
}

@Composable
private fun FavoriteCollectionCard(
  @DrawableRes image: Int,
  text: String
) {
  Surface(
    shape = MaterialTheme.shapes.small,
    modifier = Modifier.height(56.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.width(192.dp)
    ) {
      Image(
        painter = painterResource(image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.size(56.dp)
      )
      Text(
        text = text,
        style = MaterialTheme.typography.h3,
        modifier = Modifier.padding(horizontal = 16.dp)
      )
    }
  }
}

@Composable
private fun MyAppBottomNavigation(modifier: Modifier = Modifier) {
  BottomNavigation(
    backgroundColor = MaterialTheme.colors.background,
    modifier = modifier
  ) {
    BottomNavigationItem(
      icon = {
        Icon(
          imageVector = Icons.Default.Spa,
          contentDescription = null
        )
      },
      label = {
        Text(stringResource(R.string.lesson_two_home_one))
      },
      selected = true,
      onClick = {
      }
    )
    BottomNavigationItem(
      icon = {
        Icon(
          imageVector = Icons.Default.AccountCircle,
          contentDescription = null
        )
      },
      label = {
        Text(stringResource(R.string.lesson_two_home_two))
      },
      selected = false,
      onClick = {}
    )
  }
}

@Preview
@Composable
private fun PreviewSearchBar() {
  MyAppBottomNavigation()
}

@Preview
@Composable
private fun PreviewAlignYourBodyElement() {
  AlignYourBodyRow()
}

@Preview
@Composable
private fun PreviewFavoriteCard() {
  CCTheme {
    FavoriteCollectionsGrid()
  }
}

@Preview
@Composable
private fun HomeSection() {
  CCTheme {
    HomeScreen()
  }
}
