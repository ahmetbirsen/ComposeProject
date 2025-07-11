package com.example.composeproject.buildSrc

object AppConfig {

    const val applicationId = "com.lcwaikiki.android"

    const val compileSdkVersion = 35
    const val minSdkVersion = 24

    const val targetSdkVersion = 35

    const val versionCode = 4000016

    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"

    private const val versionMajor = 4
    private const val versionMinor = 0
    private const val versionPatch = 16

    val versionName
        get() = "$versionMajor.$versionMinor.$versionPatch"

    object CompileOptions {
        val javaSourceCompatibility = "21"
        val kotlinJvmTarget: String = "21"
    }
}
