package com.ironsource.aura.kronos.sample

import android.app.Application
import android.util.Log
import com.google.firebase.BuildConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.ironsource.aura.kronos.Kronos
import com.ironsource.aura.kronos.converter.gson.GsonConverter
import com.ironsource.aura.kronos.logging.Logger
import com.ironsource.aura.kronos.source.FireBaseConfigSource

private const val TAG = "KronosSample"

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.setConfigSettingsAsync(FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build())

        Kronos.init {
            context = applicationContext
            jsonConverter = GsonConverter()
            logging {
                enabled = BuildConfig.DEBUG
                logger = getLogger()
            }
            configSource { FireBaseConfigSource(this@App, firebaseRemoteConfig) }
        }
    }
}

private fun getLogger(): Logger {
    return object : Logger {
        override fun v(msg: String) {
            Log.v(TAG, msg)
        }

        override fun d(msg: String) {
            Log.d(TAG, msg)
        }

        override fun w(msg: String) {
            Log.w(TAG, msg)
        }

        override fun i(msg: String) {
            Log.i(TAG, msg)
        }

        override fun e(msg: String,
                       e: Exception?) {
            Log.e(TAG, msg)
            e?.printStackTrace()
        }
    }
}