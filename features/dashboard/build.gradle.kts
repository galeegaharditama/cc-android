plugins {
  id("com.android.library")
  id("kotlin-android")
}

android {
  compileSdk = AppConfig.compileSdk
  buildToolsVersion = AppConfig.buildToolsVersion

  defaultConfig {
    minSdk = AppConfig.minSdk
    targetSdk = AppConfig.targetSdk

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    freeCompilerArgs = freeCompilerArgs + "-Xjvm-default=all"
  }
  buildFeatures {
    viewBinding = true
    // Enables Jetpack Compose for this module
    compose = true
  }
  buildTypes {
    getByName("debug")
    getByName("release")
    create("debugcompose")
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.1.0"
  }
}

val debugcomposeImplementation by configurations

dependencies {
  implementation(project(":core:common"))
  implementation(project(":domain"))

  implementation(Libs.coil_compose)
  implementation(Libs.navigation_compose)

  debugImplementation(Libs.customview)
  debugImplementation(Libs.customview_poolingcontainer)
  debugcomposeImplementation(Libs.customview)
  debugcomposeImplementation(Libs.customview_poolingcontainer)

  debugImplementation(Libs.ui_tooling)
  debugImplementation(Libs.ui_test_manifest)
  debugcomposeImplementation(Libs.ui_tooling)
  debugcomposeImplementation(Libs.ui_test_manifest)

  implementation(Libs.accompanist_swiperefresh)
  implementation(Libs.toolbar_compose)
}
