plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("ktlint")
}

android {
    compileSdkVersion(Versions.android.compileSdk)
    buildToolsVersion(Versions.android.buildTools)

    defaultConfig {
        minSdkVersion(Versions.android.minSdk)
        targetSdkVersion(Versions.android.targetSdk)
    }
}

dependencies {
    implementation(Deps.kotlinx.coroutines)

    implementation(Deps.androidx.appCompat)
    implementation(Deps.androidx.fragmentKtx)
    implementation(Deps.androidx.material)


    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)

    implementation(Deps.tanto)

    implementation(Deps.cicerone)

    releaseImplementation(Deps.gemini)
    debugImplementation(Deps.geminiDebug)
}
