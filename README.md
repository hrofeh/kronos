Kronos
============

Kotlin Multiplatform Mobile Remote Config Management Library
---
Kronos helps you manage your Android/iOS app's remote configs with ease.
Adapt, process and validate remotely configured values so you can focus on your core business logic.

Usage
---

```kotlin
interface SyncFeatureConfig {
	val syncIntervalMinutes: Long
}

class SyncFeatureKronosConfig : SyncFeatureConfig, KronosConfig {
	override val sourceDefinition = typedSource<FirebaseConfigSource>()

	override val syncIntervalMinutes by longConfig {
		default = 60
		minValue = 15

		process { it.milliseconds.inWholeMinutes }
	}
}

val syncFeatureConfig: SyncFeatureConfig = SyncFeatureKronosConfig()
syncFeatureConfig.syncIntervalMinutes
```

Initializing the SDK
---
The SDK should be initialized once, preferably in the Application/AppDelegate class

```kotlin
Kronos.init {
	logging {
		enabled = true
		logger = NapierLogger()
	}

	configSource { FirebaseConfigSource() }
}
```

Supported config types
--------


Download
--------


License
-------
Copyright (c) 2024 Hanan Rofe Haim

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
