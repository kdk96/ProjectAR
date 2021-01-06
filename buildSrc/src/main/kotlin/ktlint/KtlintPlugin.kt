package ktlint

import Deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.register

class KtlintPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val ktlint: Configuration by target.configurations.creating
        target.dependencies {
            ktlint(Deps.ktlint)
        }
        target.tasks.register<JavaExec>("ktlint") {
            description = "Check Kotlin code style."
            group = "verification"
            classpath = ktlint
            main = "com.pinterest.ktlint.Main"
            args(
                "src/**/*.kt",
                "--reporter=plain",
                "--reporter=checkstyle,output=${target.buildDir}/reports/checkstyle/ktlint-report.xml"
            )
        }
    }
}
