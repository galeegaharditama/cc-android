plugins {
  id("com.android.application") apply false
  id("com.android.library") apply false
  id("org.jetbrains.kotlin.android") apply false
  id("io.gitlab.arturbosch.detekt").version("1.21.0")
} // Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
  dependencies {
//        classpath(Libs.com_android_tools_build_gradle)
//        classpath(Libs.kotlin_gradle_plugin)
    classpath(Libs.onesignal_gradle_plugin)
    classpath(Libs.google_services)
    classpath(Libs.firebase_crashlytics_gradle)
  }
}

dependencies {
  detektPlugins(Libs.detekt_formatting)
}

detekt {
  source = files("$projectDir")
  config = files("config/detekt.yml")
  buildUponDefaultConfig = true
  autoCorrect = true
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
