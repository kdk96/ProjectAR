plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

gradlePlugin {
    plugins.register("ktlint") {
        id = "ktlint"
        implementationClass = "ktlint.KtlintPlugin"
    }
}
