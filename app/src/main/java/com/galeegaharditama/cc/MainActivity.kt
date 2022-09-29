package com.galeegaharditama.cc

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.galeegaharditama.cc.common.ui.theme.CCTheme
import com.galeegaharditama.cc.navigation.CathConferenceNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CCTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
        ) {
          MainScreen(
            this
          )
        }
      }
    }
  }
}

@Composable
private fun MainScreen(
  activity: Activity
) {
  val navController = rememberNavController()

  val systemUiController = rememberSystemUiController()
  if (isSystemInDarkTheme()) {
    systemUiController.setSystemBarsColor(
      color = Color.Transparent
    )
  } else {
    systemUiController.setSystemBarsColor(
      color = Color.White
    )
  }

  Scaffold { innerPadding ->
    CathConferenceNavHost(
      activity = activity,
      navController = navController,
      modifier = Modifier.padding(innerPadding)
    )
  }
}
