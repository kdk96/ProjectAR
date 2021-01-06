object Deps {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.android.gradlePlugin}"

    object android {
        const val desugarJdkLibs = "com.android.tools:desugar_jdk_libs:${Versions.android.desugarJdkLibs}"
    }

    object androidx {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.androidx.appCompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.androidx.core}"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.androidx.lifecycle}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.androidx.fragment}"
        const val material = "com.google.android.material:material:${Versions.androidx.material}"
        const val preference = "androidx.preference:preference-ktx:${Versions.androidx.preference}"
    }

    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    const val tanto = "com.kdk96.tanto:tanto-android:${Versions.tanto}"

    const val cicerone = "com.github.terrakok:cicerone:${Versions.cicerone}"

    const val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
}