[![Maven Central](https://img.shields.io/maven-central/v/com.hrofeh.kronos/kronos)](https://mvnrepository.com/artifact/com.hrofeh.kronos)
[![Kotlin](https://img.shields.io/badge/kotlin-1.9.22-blue.svg?logo=kotlin)](http://kotlinlang.org)

Kronos
============

Kotlin Multiplatform Mobile Remote Config Management Library
---
Kronos helps you manage your Android/iOS app's remote configs with ease.

Define your remote config contract using kotlin property delegation and keep you remote configs in order.

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

Config source
--------
Kronos can potentially support any config source, e.g Firebase Remote Config.
To add a config sources to Kronos, implement the ```ConfigSource``` interface.

Config sources can be either added when initializing the SDK or at runtime by retrieving the config source repository and adding the source to it.

```kotlin
Kronos.configSourceRepository.addSource(FirebaseConfigSource())
```

Constructing your config interface
--------
Implement the ```KronosConfig``` interface:
1. Define sourceDefinition, i.e what source should be used to resolve the config values and 
2. Define your config properties using the provided config delegates.

```kotlin
class SyncFeatureKronosConfig : SyncFeatureConfig, KronosConfig {
	override val sourceDefinition = typedSource<FirebaseConfigSource>()

	override val syncIntervalMinutes by longConfig {
		default = 60
	}
}
```

If a config source should only be scoped to a specific feature, you can define a scoped source and provide an instance:

```kotlin
class SyncFeatureKronosConfig(source:ConfigSource) : SyncFeatureConfig, KronosConfig {
	override val sourceDefinition = scopedSource(source)

	override val syncIntervalMinutes by longConfig {
		default = 60
	}
}
```

Defining config properties
--------
Kronos uses Kotlin's property delegates to define the config contract.
A class implementing the ```KronosConfig``` will get access to config properties extension functions. 

The the following config property types are supported:
Int, Long, Float, Double, String, Boolean, Set<String>

Each config property must define a key and a default value.

```kotlin
class SyncFeatureKronosConfig : SyncFeatureConfig, KronosConfig {
    override val sourceDefinition = typedSource<FirebaseConfigSource>()

    override val syncIntervalMinutes by longConfig {
        default = 60
    }
}
```

By default, Kronos will use the property name as a key to resolve the config value from the source.
Alternatively, you can specify a custom key by providing assigning the key property.

To provide a default value you can either assign the ```default``` property or provide a default value lambda to be resolved lazily.

Config properties processing and adapting
--------
Kronos allows you to process the remotely config value before returning it.

In the following example the remove value is configured in milliseconds, but the app uses minutes as the time unit.
```kotlin
val syncIntervalMinutes by longConfig {
    default = 60
    process { it.milliseconds.inWholeMinutes }
}
```

Sometimes, as part of the config processing, we want to convert it to another type.
Kronos supports this using adapted configs.

```kotlin
val syncIntervalMinutes by adaptedIntConfig<String> {
    default = 60
    adapt { 
		get { it.toString() }
	}
}
```

Config properties validation
--------
Sometimes remote config values can be invalid, e.g negative values for time configs.
Kronos allows you to validate the remotely configured value before returning it by defining constraints.

```kotlin
val syncIntervalMinutes by longConfig {
    default = 60
	constraint {
		allowIf { it >= 0 }
		fallbackTo = 15
	}
}
```

If validation fails, Kronos will return the default value.
Alternatively, you can provide a fallback value for each constraint to be returned in case of validation failure.
```kotlin
val syncIntervalMinutes by longConfig {
    default = 60
	constraint {
		allowIf { it >= 0 }
		fallbackTo = 15
	}
}
```

Sometimes you don't need a special implementation for your constraint, in that case you can use the built-in suite of constraints Kronos provides, such as:
```minValue```, ```maxValue```, ```allowList``` and more!

If you have a custom constraint you want to reuse across configs, this can easily be achieved by creating an extension function/property on the ```com.hrofeh.kronos.config.Config class```.

Download
--------
Kronos is available on MavenCentral.

```groovy
dependencies {
    implementation 'com.hrofeh.kronos:kronos:VERSION'
}
```

Extensions
--------
The kronos base module is meant to be lean and serve common use-cases.
As your app's remote config gets more advanced, you can use Kronos' extension modules:

Json extension
--------
Kronos provides a Json extension module to create json config properties.

When initializing the SDK, you need to initialize the extension and provide a json serializer:
```kotlin
Kronos.init {
    extension {
        json {
            serializer = KronosKotlinxSerializer()
        }
    }
}
```

Kronos doesn't have a built-in json serializer, but you can either:
1. Use any json serializer that implements the ```KronosJsonSerializer``` interface.
2. Use the kotlinx-serialization json serializer by adding the the extension module.

Then you can use the ```jsonConfig``` extension to define json config properties:
```kotlin
val syncDays by jsonConfig<List<Int>> {
    default = emptyList()
}
```

Adding the json extension dependency:
```groovy
dependencies {
    implementation 'com.hrofeh.kronos:extension-json:VERSION'
}
```

To use the kotlinx-serialization json serializer, add the following dependency:
```groovy
dependencies {
    implementation 'com.hrofeh.kronos:extension-json-kotlinx:VERSION'
}
```

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
