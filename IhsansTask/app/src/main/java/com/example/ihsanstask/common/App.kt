package com.example.ihsanstask.common

import android.content.Context
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.example.ihsanstask.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.Locale

@HiltAndroidApp
class App : LocalizationApplication() {

    override fun getDefaultLanguage(context: Context): Locale = Locale.getDefault()

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var instance: App
            private set
    }
}
