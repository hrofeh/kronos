package com.ironsource.aura.kronos.sample

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.ironsource.aura.kronos.sample.config.SomeFeatureConfig

class MainActivity : AppCompatActivity() {

	private lateinit var titleView: TextView

	private val someFeatureConfig = SomeFeatureConfig()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		titleView = findViewById(R.id.title)

		loadFireBaseConfig()
	}

	private fun loadFireBaseConfig() {
		FirebaseRemoteConfig.getInstance()
			.fetch(0)
			.addOnSuccessListener {
				FirebaseRemoteConfig.getInstance().activate()
				onFireBaseConfigLoaded()
			}
	}

	private fun onFireBaseConfigLoaded() {
		Toast.makeText(this, "FireBase remote config loaded", Toast.LENGTH_SHORT).show()

		titleView.text = someFeatureConfig.someMap.toString()
	}
}