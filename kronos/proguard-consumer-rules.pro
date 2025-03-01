# Rules that need to be applied on the main app
-keep class com.hrofeh.kronos.** { *; }

-keepclassmembernames class * implements com.hrofeh.kronos.config.FeatureRemoteConfig { *; }