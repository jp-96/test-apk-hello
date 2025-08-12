plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Make sure that you have the Google services Gradle plugin
    id("com.google.gms.google-services")
    // Add the App Distribution Gradle plugin
    id("com.google.firebase.appdistribution")
}

android {
    namespace = "com.example.test_apk_hello"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.test_apk_hello"
        minSdk = 27
        targetSdk = 34
        versionCode = 2
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val signingEnabled = System.getenv("GITHUB_ACTIONS")?.toBoolean() ?: false
            if (signingEnabled) {
                storeFile = file(System.getenv("STORE_FILE"))
                storePassword = System.getenv("STORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD")
            }
        }
    }
    
    buildTypes {
        getByName("release") {
            val signingEnabled = System.getenv("GITHUB_ACTIONS")?.toBoolean() ?: false
            signingConfig = if (signingEnabled) signingConfigs.getByName("release") else null
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            firebaseAppDistribution {
                artifactType = "APK"
                val envReleaseNotes = System.getenv("FIREBASE_RELEASE_NOTES")
                if (!envReleaseNotes.isNullOrBlank()) {
                    releaseNotes = envReleaseNotes
                }
                val envGroups = System.getenv("FIREBASE_GROUPS")
                if (!envGroups.isNullOrBlank()) {
                    groups = envGroups
                }
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))

}