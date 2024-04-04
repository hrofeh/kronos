package com.aura.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import com.aura.myapplication.config.MainScreenConfig
import com.aura.myapplication.ui.theme.KronosTheme

class MainActivity : ComponentActivity() {

	private val screenConfig = MainScreenConfig()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			KronosTheme {
				Column {
					Text(
						text = screenConfig.texts["greeting"]?.joinToString(" ") ?: "",
					)
					Text(
						text = screenConfig.defaultedConfig,
					)
				}
			}
		}
	}
}