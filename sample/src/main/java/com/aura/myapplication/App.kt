package com.aura.myapplication

import android.app.Application
import com.aura.myapplication.config.source.MapConfigSource
import com.hananrh.kronos.Kronos
import com.hananrh.kronos.extensions.json.json
import com.hananrh.kronos.extensions.resources.kotlinx.KotlinxSerializer
import com.hananrh.kronos.extensions.resources.resources
import com.hananrh.kronos.logging.AndroidKronosLogger

internal class App : Application() {

	override fun onCreate() {
		super.onCreate()

		Kronos.init {
			logging {
				enabled = true
				logger = AndroidKronosLogger()
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

				resources {
					resources = applicationContext.resources
				}
			}
		}
	}
}