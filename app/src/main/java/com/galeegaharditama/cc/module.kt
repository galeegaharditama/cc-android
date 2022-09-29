package com.galeegaharditama.cc

import com.galih.library.ConnectivityInterceptorImpl
import com.galih.library.provideOkHttpClient
import com.galih.library.provideRetrofit
import org.koin.dsl.module

val appModule = module {
  single {
    provideOkHttpClient(BuildConfig.DEBUG).apply {
      addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
          .addHeader("Accept", "application/json")
        requestBuilder.addHeader("api-token", BuildConfig.API_KEY)
        val request = requestBuilder.build()
        it.proceed(request)
      }
      addInterceptor(ConnectivityInterceptorImpl(context = get()))
    }
  }

  single {
    provideRetrofit(
      baseUrl = if (BuildConfig.DEBUG) BuildConfig.URL_DEBUG else BuildConfig.URL_PROD,
      okHttpClient = get(),
      moshi = get()
    )
  }
}
