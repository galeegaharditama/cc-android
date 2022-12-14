plugins {
  id("com.android.library")
  id("kotlin-android")
  id("kotlin-kapt")
}

group = "com.github.galeegaharditama"

android {
  compileSdk = AppConfig.compileSdk

  defaultConfig {
    minSdk = AppConfig.minSdk
    targetSdk = AppConfig.targetSdk
//        versionCode = 1
//        versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    getByName("debug")
    getByName("release")
    create("debugcompose")
  }
  compileOptions {
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  api(Libs.timber)
  api(Libs.koin_android)
  api(Libs.retrofit)
  api(Libs.logging_interceptor)
//  api(Libs.converter_gson)
  api(Libs.moshi)
  api(Libs.moshi_kotlin)
  api(Libs.converter_moshi)
  kapt(Libs.moshi_kotlin_codegen)

  implementation(Libs.navigation_fragment_ktx)
  implementation("id.zelory:compressor:_")
  implementation("cat.ereza:customactivityoncrash:_")

  implementation(Libs.constraintlayout)
  api(Libs.com_google_android_material_material)

  implementation(Libs.room_runtime)
  implementation(Libs.room_ktx)
  kapt(Libs.room_compiler)

  testImplementation(Libs.junit_junit)
  testImplementation(Libs.mockk)
  androidTestImplementation(Libs.androidx_test_ext_junit)
  androidTestImplementation(Libs.espresso_core)
}
