plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.stack.kotlin.parcelize)
    alias(libs.plugins.stack.hilt.plugin)
    alias(libs.plugins.stack.ksp)
}
val config: (String) -> String = { k -> "\"${project.property(k) as String}\"" }

android {
    namespace = "com.example.composeproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.composeproject"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        buildConfig = true
        //
    }

    buildTypes {
        release {
            applicationIdSuffix = ""

            buildConfigField("String", "ENVIRONMENT_VARIABLE", config("PROD_ENVIRONMENT"))
            buildConfigField("String", "BASE_URL", config("prod_url"))

            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        create("dev") {
            applicationIdSuffix = ".dev"

            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false

            buildConfigField("String", "ENVIRONMENT_VARIABLE", config("TEST_ENVIRONMENT"))
            buildConfigField("String", "BASE_URL", config("test_url"))
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core Android
    implementation(libs.androidx.core.ktx)


    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    
    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.compose)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.runtime.android)
    
    // Compose UI
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.util)
    implementation(libs.androidx.flowrow)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    
    // Compose Material
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.window)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material.core)
    
    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.dynamic)
    
    // Gradle
    //implementation(libs.gradle)
    implementation(libs.hilt.android.gradle.plugin)
    
    // Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.stack.hilt.android)
    ksp(libs.stack.hilt.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    
    // Data Store
    implementation(libs.androidx.data.store)
    
    // Network
    implementation(libs.bundles.network)

    /*
    Chucker implementasyonu için flavors kullanılacak
     */
    //implementation(libs.network.chucker.library)
    debugImplementation(libs.network.chucker.library)
    releaseImplementation(libs.network.chucker.library.noop)

    // Image Loading
    implementation(libs.coil.kt.svg)
    implementation(libs.coil.kt.compose)
    
    // Utilities
    implementation(libs.gson)
    implementation(libs.stack.moshi.kotlin)
    implementation(libs.stack.jwt)
    implementation(libs.jsoup)
    
    // Coroutines
    implementation(libs.stack.coroutines)

    // Kotlin
    implementation(libs.stack.kotlin.reflect)
    implementation(libs.kotlinx.serialization.json)

    // Accompanist
    implementation(libs.stack.accompanist.systemui)
}