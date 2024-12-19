plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("org.jetbrains.kotlin.plugin.serialization") version ("2.0.0")
}

android {
    namespace = "com.included.zoom"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.included.zoom"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // TODO: As written in the Zoom documentation, apps shouldn't contain the SDK secrets
        // @see: https://developers.zoom.us/docs/meeting-sdk/auth/#generate-a-meeting-sdk-jwt
        buildConfigField("String", "ZOOM_CLIENT_ID", "\"vfw2P1nYQO6ckKVJYG3WKw\"")
        buildConfigField("String", "ZOOM_CLIENT_SECRET", "\"yzekXIReIRzVyJbah4B8MblVj8xWZEec\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":mobilertc"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    implementation(libs.jwt)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.material3)
}