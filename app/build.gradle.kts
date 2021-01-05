plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Versions.android.compileSdk)
    buildToolsVersion(Versions.android.buildTools)

    defaultConfig {
        applicationId = "com.kdk96.projectar"
        minSdkVersion(Versions.android.minSdk)
        targetSdkVersion(Versions.android.targetSdk)
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring(Deps.android.desugarJdkLibs)

    implementation(Deps.androidx.appCompat)
    implementation(Deps.androidx.fragmentKtx)

    implementation(Deps.dagger)
    kapt(Deps.daggerCompiler)

    implementation(Deps.tanto)

    implementation(project(":common"))

//    implementation "androidx.appcompat:appcompat:$appcompat_version"
//
//            implementation "io.reactivex.rxjava2:rxjava:$rxjava_version"
//    implementation "io.reactivex.rxjava2:rxandroid:$rxandroid_version"
//
//    implementation "com.arello-mobile:moxy:$moxy_version"
//    kapt "com.arello-mobile:moxy-compiler:$moxy_version"
//
//    implementation "ru.terrakok.cicerone:cicerone:$cicerone_version"
//
//    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
//    implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofit_version"
//    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
//
//    implementation kotlinx . serialization
//            implementation ktor . serialization
//
//            releaseImplementation(gemini)
//    debugImplementation(geminiDebug)
//
//    implementation(kotlinx.coroutines)
//    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
//
//    implementation project (':auth')
//    implementation project (':firebase-auth-api')
//    implementation project (':feature-auth')
//
//    implementation project (':feature-mainflow')
//    implementation project (':glide')
//    implementation "com.github.bumptech.glide:glide:$glide_version"
//    implementation project (':feature-quests')
//    implementation "com.jakewharton.threetenabp:threetenabp:$threetenabp_version"
//    implementation project (':database')
//    implementation "androidx.room:room-runtime:$room_version"
//    implementation project (':feature-prizes')
//    implementation project (':feature-settings')
//
//    implementation project (':feature-questinfo')
//
//    implementation project (':feature-questflow')
}
