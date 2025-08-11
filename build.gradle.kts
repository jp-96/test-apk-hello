// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // Make sure that you have the Google services Gradle plugin dependency
    id("com.google.gms.google-services") version "4.4.3" apply false
    // Add the dependency for the App Distribution Gradle plugin
    id("com.google.firebase.appdistribution") version "5.1.1" apply false
}