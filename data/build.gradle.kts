plugins {
  id("com.android.library")
  id("kotlin-android")
  id("kotlin-kapt")
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
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
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

dependencies {
  implementation(project(":core:my_helper_android"))
  implementation(project(":domain"))

  kapt(Libs.moshi_kotlin_codegen)
}
