plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("ktlint")
}

dependencies {
    implementation(Deps.kotlinx.serialization)

    implementation(Deps.ktor.core)

    implementation(project(":auth-api"))
}
