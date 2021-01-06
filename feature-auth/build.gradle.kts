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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Deps.kotlinx.coroutines)

    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)

    implementation(Deps.tanto)

    implementation(Deps.androidx.appCompat)
    implementation(Deps.androidx.fragmentKtx)
    implementation(Deps.androidx.material)
    implementation(Deps.androidx.constraintLayout)

    implementation(Deps.cicerone)

    releaseImplementation(Deps.gemini)
    debugImplementation(Deps.geminiDebug)

    implementation(project(":common"))

//
//            implementation "androidx.constraintlayout:constraintlayout:$constraintlayout_version"
//    implementation "com.google.android.material:material:$material_version"
//
//            implementation "com.arello-mobile:moxy:$moxy_version"
//    kapt "com.arello-mobile:moxy-compiler:$moxy_version"
//
//    implementation project (':auth')
//
//    implementation "io.reactivex.rxjava2:rxjava:$rxjava_version"
//    implementation "io.reactivex.rxjava2:rxandroid:$rxandroid_version"
//
//    implementation "com.jakewharton.rxbinding3:rxbinding-core:$rxbinding_version"
//
//    implementation "ru.terrakok.cicerone:cicerone:$cicerone_version"
//
//    testImplementation "junit:junit:$junit_version"
//    testImplementation "org.mockito:mockito-core:$mockito_version"
}
