# Rules that need to be applied on the main app
-keep class com.ironsource.aura.kronos.** { *; }

-keepclassmembernames class * implements com.ironsource.aura.kronos.config.FeatureRemoteConfig { *; }
