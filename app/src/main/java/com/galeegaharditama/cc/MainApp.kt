package com.galeegaharditama.cc

import android.app.Application
import com.galeegaharditama.cc.dashboard.dashboardModule
import com.galeegaharditama.cc.data.dataModule
import com.galeegaharditama.cc.domain.domainModule
import com.galeegaharditama.cc.form.formModule
import com.galeegaharditama.cc.login.loginModule
import com.galeegaharditama.cc.register.registerModule
import com.galih.library.DebugTree
import com.onesignal.OneSignal
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApp : Application() {
  private val _oneSignalID = "8882bcb0-bfda-4642-93e1-724f29b4955e"

  override fun onCreate() {
    super.onCreate()

    // Enable verbose OneSignal logging to debug issues if needed.
    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

    // OneSignal Initialization
    OneSignal.initWithContext(this)
    OneSignal.setAppId(_oneSignalID)

    startKoin {
      // Inject Android Context
      androidContext(this@MainApp)
      modules(
        listOf(
          dataModule,
          appModule,
          domainModule,
          loginModule,
          registerModule,
          dashboardModule,
          formModule
        )
      )
    }
    if (BuildConfig.DEBUG) {
      Timber.plant(DebugTree())
    }

//        createChannelsNotification()
  }

  /*private fun createChannelsNotification() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
          val channelUpload = NotificationChannel(
              NOTIF_CHANNEL_UPLOAD_FILE, "Upload File", NotificationManager.IMPORTANCE_LOW
          )
          // ADD MORE CHANNEL NOTIFICATION HERE

          val managerNotification = getSystemService(NotificationManager::class.java)
          managerNotification.notificationChannels.forEach {
              Timber.e("${it.name} ${it.id}")
          }
          managerNotification.createNotificationChannel(channelUpload)
      }
  }*/
}
