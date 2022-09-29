import java.net.URI

pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven { url = URI("https://jitpack.io") }
  }
}

plugins {
  // See https://jmfayard.github.io/refreshVersions
  id("de.fayard.refreshVersions") version "0.50.1"
}

refreshVersions {
  enableBuildSrcLibs()
  featureFlags {
    enable(de.fayard.refreshVersions.core.FeatureFlag.LIBS)
  }
}

rootProject.name = "Cath Conference"
include(":app")
include(":core:my_helper_android", ":core:common")
include(":data", ":domain")
include(":features:login")
include(":features:register")
include(":features:dashboard")
include(":features:form")
