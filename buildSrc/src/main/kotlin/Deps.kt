object Deps {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.android.gradlePlugin}"

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
}