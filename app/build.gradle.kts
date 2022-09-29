import org.jetbrains.kotlin.konan.properties.Properties

plugins {
  id("com.onesignal.androidsdk.onesignal-gradle-plugin")
  id("com.android.application")
  id("kotlin-android")
  kotlin("android")
  kotlin("kapt")
  id("com.google.firebase.crashlytics")
  id("com.google.gms.google-services")
}

android {
  compileSdk = AppConfig.compileSdk

  defaultConfig {
    applicationId = "com.galeegaharditama.cc"
    minSdk = AppConfig.minSdk
    targetSdk = AppConfig.targetSdk
    versionCode = AppConfig.versionCode
    versionName = AppConfig.versionName
    // used by Room, to test migrations
    javaCompileOptions {
      annotationProcessorOptions {
        argument("room.schemaLocation", "$projectDir/schemas")
        argument("room.incremental", "true")
      }
    }
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    multiDexEnabled = true
    vectorDrawables.useSupportLibrary = true
  }

  buildTypes {
    getByName("debug") {
      manifestPlaceholders["appName"] = "Cath Conference"
      resValue("string", "app_name", "Cath Conference")
    }

    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }

    create("debugcompose") {
      initWith(buildTypes.getByName("debug"))
      applicationIdSuffix = ".development.compose"
      versionNameSuffix = "-development-compose"
      manifestPlaceholders["appName"] = "Cath Conference (Compose)"
      resValue("string", "app_name", "Cath Conference (Compose)")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
  }
  buildFeatures {
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
  buildTypes.forEach {
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())

    val urlDev = properties.getProperty("url_demo")
    it.buildConfigField("String", "URL_DEBUG", urlDev)

    val urlProduction = properties.getProperty("url_prod")
    it.buildConfigField("String", "URL_PROD", urlProduction)

    val apiKey = properties.getProperty("api_key")
    it.buildConfigField("String", "API_KEY", apiKey)
  }
}

val debugcomposeImplementation by configurations

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

  implementation(project(":core:common"))
  implementation(project(":data"))
  implementation(project(":domain"))

  // FEATURES
  implementation(project(":features:login"))
  implementation(project(":features:register"))
  implementation(project(":features:dashboard"))
  implementation(project(":features:form"))

  // FIREBASE
  implementation(platform(Libs.firebase_bom))
  implementation(Libs.firebase_messaging)
  implementation(Libs.firebase_crashlytics_ktx)
  implementation(Libs.firebase_analytics_ktx)

  implementation(Libs.coil_compose)
  implementation(Libs.navigation_compose)
  implementation(Libs.accompanist_systemuicontroller)

  testImplementation(Libs.junit_junit)
  androidTestImplementation(Libs.androidx_test_ext_junit)
  androidTestImplementation(Libs.espresso_core)
  androidTestImplementation(Libs.ui_test_junit4)

  debugImplementation(Libs.ui_tooling)
  debugImplementation(Libs.ui_test_manifest)
  debugcomposeImplementation(Libs.ui_tooling)
  debugcomposeImplementation(Libs.ui_test_manifest)

  debugImplementation(Libs.customview)
  debugImplementation(Libs.customview_poolingcontainer)
  debugcomposeImplementation(Libs.customview)
  debugcomposeImplementation(Libs.customview_poolingcontainer)

  implementation(Libs.onesignal)
}
