# Rules that need to be applied on the main app
-keep class com.hananrh.kronos.** { *; }

-keepclassmembernames class * implements com.hananrh.kronos.FeatureRemoteConfig { *; }