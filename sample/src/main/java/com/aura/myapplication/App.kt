package com.aura.myapplication

import android.app.Application
import com.aura.myapplication.config.source.MapConfigSource
import com.hananrh.kronos.Kronos
import com.hananrh.kronos.extensions.json.json
import com.hananrh.kronos.extensions.json.kotlinx.KotlinxSerializer
import com.hananrh.kronos.logging.AndroidLogger

internal class App : Application() {

	override fun onCreate() {
		super.onCreate()

		Kronos.init {
			context = applicationContext

			logging {
				enabled = true
				logger = AndroidLogger()
			}

			configSource {
				MapConfigSource(
					"texts" to "{\"greeting\": [\"Hello\", \"World!\"]}"
				)
			}

			extensions {
				json {
					serializer = KotlinxSerializer()
				}
			}
		}
	}
}