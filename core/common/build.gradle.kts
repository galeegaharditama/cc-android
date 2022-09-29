plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
}

android {
  compileSdk = AppConfig.compileSdk

  defaultConfig {
    minSdk = AppConfig.minSdk
    targetSdk = AppConfig.targetSdk

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    multiDexEnabled = true
    vectorDrawables.useSupportLibrary = true
//        consumerProguardFiles("consumer-rules.pro")
  }
  compileOptions {
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
  }
  buildFeatures {
    viewBinding = true
    // Enables Jetpack Compose for this module
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.1.0"
  }
  packagingOptions {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
  buildTypes {
    getByName("debug")
    getByName("release")
    create("debugcompose")
  }
}

val debugcomposeImplementation by configurations

dependencies {
  implementation(project(":domain"))
  api(project(":core:my_helper_android"))

  api(Libs.ui)
  api(Libs.androidx_compose_material_material)
  api(Libs.ui_tooling_preview)
  api(Libs.lifecycle_runtime_ktx)
  api(Libs.activity_compose)
  api(Libs.foundation)
  api(Libs.material_icons_extended)
  api(Libs.lifecycle_viewmodel_compose)
  api(Libs.animation)
  api(Libs.koin_androidx_compose)
  api(Libs.navigation_compose)
  api(Libs.ui_util)

  // accompanist
  implementation(Libs.accompanist_systemuicontroller)

  // Material 3
  implementation("androidx.compose.material3:material3:1.0.0-beta01")
  implementation("androidx.compose.material3:material3-window-size-class:1.0.0-beta01")

  debugImplementation(Libs.ui_tooling)
  debugImplementation(Libs.ui_test_manifest)
  debugcomposeImplementation(Libs.ui_tooling)
  debugcomposeImplementation(Libs.ui_test_manifest)

  debugImplementation(Libs.customview)
  debugImplementation(Libs.customview_poolingcontainer)
  debugcomposeImplementation(Libs.customview)
  debugcomposeImplementation(Libs.customview_poolingcontainer)

  testImplementation(Libs.junit_junit)
  androidTestImplementation(Libs.androidx_test_ext_junit)
  androidTestImplementation(Libs.espresso_core)
  androidTestImplementation(Libs.ui_test_junit4)
}
